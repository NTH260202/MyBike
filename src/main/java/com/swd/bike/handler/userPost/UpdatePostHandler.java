package com.swd.bike.handler.userPost;

import com.swd.bike.common.BaseConstant;
import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.userPost.request.UpdatePostRequest;
import com.swd.bike.dto.userPost.response.PostResponse;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Post;
import com.swd.bike.entity.Station;
import com.swd.bike.entity.Vehicle;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.enums.TripRole;
import com.swd.bike.enums.VehicleStatus;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.ContextService;
import com.swd.bike.service.StationService;
import com.swd.bike.service.interfaces.IPostService;
import com.swd.bike.service.interfaces.IStationService;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdatePostHandler extends RequestHandler<UpdatePostRequest, PostResponse> {
    private final ITripService tripService;
    private final IPostService postService;
    private final IStationService stationService;
    private final ContextService contextService;

    @Override
    @Transactional
    public PostResponse handle(UpdatePostRequest request) {
        Account account = contextService.getLoggedInUser();

        Post post = postService.getPost(request.getId());
        if (!postService.isPostActive(post)) {
            throw new InternalException(ResponseCode.POST_ERROR_NOT_FOUND);
        }

        if (!postService.isAuthor(account, post)) {
            log.error("Current user is not post's author");
            throw new InternalException(ResponseCode.FAILED);
        }

        List<Account> applications = post.getApplications();
        if (applications != null && !applications.isEmpty()) {
            throw new InternalException(ResponseCode.POST_ERROR_INVALID_STATUS);
        }

        // Update description
        String description = request.getDescription();
        if (StringUtils.isNotBlank(description)) {
            post.setDescription(description);
        }

        // Update role
        TripRole role = request.getRole();
        if (role != null) {
            post.setRole(role);
        }

        Vehicle vehicle = account.getVehicle();
        if (TripRole.GRABBER.equals(post.getRole()) && (vehicle == null || !VehicleStatus.APPROVED.equals(vehicle.getStatus()))) {
            throw new InternalException(ResponseCode.POST_ERROR_UNREGISTERED_VEHICLE);
        }

        // Update start time
        LocalDateTime startTime = request.getStartTime();
        if (startTime != null && !startTime.equals(post.getStartTime())) {
            post.setStartTime(startTime);
        }

        // Update start station
        Long startStationId = request.getStartStationId();
        if (startStationId != null) {
            Station station = stationService.findStation(startStationId);
            if (!stationService.isActiveStation(station)) {
                throw new InternalException(ResponseCode.POST_ERROR_STATION_NOT_FOUND);
            }
            post.setStartStation(station);
        }

        // Update end station
        Long endStationId = request.getEndStationId();
        if (endStationId != null && !endStationId.equals(post.getEndStation().getId())) {
            Station station = stationService.findStation(endStationId);
            if (!stationService.isActiveStation(station)) {
                throw new InternalException(ResponseCode.POST_ERROR_STATION_NOT_FOUND);
            }
            post.setEndStation(station);
        }

        // Validate
        LocalDateTime postStartTime = post.getStartTime();
        LocalDateTime now = LocalDateTime.now();
        if (postStartTime.isBefore(now) || postStartTime.equals(now)) {
            throw new InternalException(ResponseCode.POST_ERROR_INVALID_TIME);
        }

        if (Objects.equals(post.getStartStation().getId(), post.getEndStation().getId())) {
            throw new InternalException(ResponseCode.POST_ERROR_INVALID_STATION);
        }

        Station startStation = stationService.findStation(post.getStartStation().getId());
        boolean isAllowedEndStation = startStation.getNextStation()
                .stream()
                .anyMatch(station -> station.getId().equals(post.getEndStation().getId()));
        if (!isAllowedEndStation) {
            throw new InternalException(ResponseCode.POST_ERROR_INVALID_END_STATION);
        }

        Account author = contextService.getLoggedInUser();
        Post existPost = postService.findAnyPostStartAt(author, post.getStartTime(), BaseConstant.POST_THRESHOLD_IN_MINUTES);
        if (existPost != null && !existPost.getId().equals(post.getId())) {
            throw new InternalException(ResponseCode.POST_ERROR_CONFLICT_TIME);
        }

        Post savedPost = postService.savePost(post);
        postService.scheduleClearExpiredPost(savedPost);

        return new PostResponse(savedPost);
    }
}
