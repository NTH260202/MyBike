package com.swd.bike.handler.userPost;

import com.swd.bike.common.BaseConstant;
import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.userPost.request.CreatePostRequest;
import com.swd.bike.dto.userPost.response.PostResponse;
import com.swd.bike.entity.*;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.enums.TripRole;
import com.swd.bike.enums.VehicleStatus;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.ContextService;
import com.swd.bike.service.interfaces.IPostService;
import com.swd.bike.service.interfaces.IStationService;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CreatePostHandler extends RequestHandler<CreatePostRequest, PostResponse> {

    private final IPostService postService;
    private final ITripService tripService;
    private final IStationService stationService;
    private final ContextService contextService;

    @Override
    @Transactional
    public PostResponse handle(CreatePostRequest request) {
        Account account = contextService.getLoggedInUser();
        if (!account.getIsUpdated()) {
            throw new InternalException(ResponseCode.POST_ERROR_UNUPDATED_ACCOUNT);
        }

        // Validate current trip
        Trip inProgressTrip = tripService.getCurrentTrip(account);
        if (inProgressTrip != null) {
            throw new InternalException(ResponseCode.TRIP_ERROR_ON_GOING_TRIP);
        }

        Vehicle vehicle = account.getVehicle();
        if (TripRole.GRABBER.equals(request.getRole())
                && (vehicle == null || !VehicleStatus.APPROVED.equals(vehicle.getStatus()))) {
            throw new InternalException(ResponseCode.POST_ERROR_UNREGISTERED_VEHICLE);
        }

        // Validate time
        LocalDateTime startTime = request.getStartTime();
        LocalDateTime now = LocalDateTime.now();
        if (startTime.isBefore(now) || startTime.equals(now)) {
            throw new InternalException(ResponseCode.POST_ERROR_INVALID_TIME);
        }

        // Validate existing post
        Post existedPost = postService.findAnyPostStartAt(account, request.getStartTime(), BaseConstant.POST_THRESHOLD_IN_MINUTES);
        if (existedPost != null) {
            throw new InternalException(ResponseCode.POST_ERROR_CONFLICT_TIME);
        }

        // validate station
        if (Objects.equals(request.getStartStationId(), request.getEndStationId())) {
            throw new InternalException(ResponseCode.POST_ERROR_INVALID_STATION);
        }

        Station startStation = stationService.findStation(request.getStartStationId());
        Station endStation = stationService.findStation(request.getEndStationId());
        if (!stationService.isActiveStation(startStation) || !stationService.isActiveStation(endStation)) {
            throw new InternalException(ResponseCode.POST_ERROR_STATION_NOT_FOUND);
        }

        boolean isAllowedEndStation = startStation.getNextStation()
                .stream()
                .anyMatch(station -> station.getId().equals(endStation.getId()));
        if (!isAllowedEndStation) {
            throw new InternalException(ResponseCode.POST_ERROR_INVALID_END_STATION);
        }

        Post post = new Post();
        post
                .setRole(request.getRole())
                .setDescription(request.getDescription())
                .setStartStation(startStation)
                .setEndStation(endStation)
                .setAuthor(account)
                .setStartTime(request.getStartTime());

        Post saved = postService.savePost(post);
        postService.scheduleClearExpiredPost(post);

        return new PostResponse(saved);
    }
}