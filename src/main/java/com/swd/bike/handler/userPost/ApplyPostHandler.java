package com.swd.bike.handler.userPost;

import com.swd.bike.common.BaseConstant;
import com.swd.bike.common.NotificationConstant;
import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.notification.dtos.NotificationDto;
import com.swd.bike.dto.userPost.request.ApplyPostRequest;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Post;
import com.swd.bike.entity.Trip;
import com.swd.bike.entity.Vehicle;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.enums.TripRole;
import com.swd.bike.enums.VehicleStatus;
import com.swd.bike.enums.notification.NotificationAction;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.ContextService;
import com.swd.bike.service.interfaces.IPostService;
import com.swd.bike.service.interfaces.IPushNotificationService;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ApplyPostHandler extends RequestHandler<ApplyPostRequest, StatusResponse> {

    private final IPostService postService;
    private final ITripService tripService;
    private final ContextService contextService;
    private final IPushNotificationService pushNotificationService;

    @Override
    @Transactional
    public StatusResponse handle(ApplyPostRequest request) {
        Account currentUser = contextService.getLoggedInUser();
        if (!currentUser.getIsUpdated()) {
            throw new InternalException(ResponseCode.POST_ERROR_UNUPDATED_ACCOUNT);
        }

        Post post = postService.getPost(request.getId());

        if (!postService.isPostActive(post)) {
            throw new InternalException(ResponseCode.POST_ERROR_NOT_FOUND);
        }
        if (Objects.equals(post.getAuthor().getId(), currentUser.getId())) {
            throw new InternalException(ResponseCode.POST_ERROR_SELF_APPLY);
        }

        Vehicle vehicle = currentUser.getVehicle();
        if (TripRole.PASSENGER.equals(post.getRole()) && (vehicle == null || !VehicleStatus.APPROVED.equals(vehicle.getStatus()))) {
            throw new InternalException(ResponseCode.POST_ERROR_UNREGISTERED_VEHICLE);
        }

        Trip inProgressTrip = tripService.getCurrentTrip(currentUser);
        if (inProgressTrip != null) {
            throw new InternalException(ResponseCode.TRIP_ERROR_ON_GOING_TRIP);
        }

        Post existedPost = postService.findAnyPostStartAt(currentUser, post.getStartTime(), BaseConstant.POST_THRESHOLD_IN_MINUTES);
        if (existedPost != null) {
            throw new InternalException(ResponseCode.POST_ERROR_CONFLICT_TIME);
        }

        // Apply
        if (post.getApplications() == null) {
            post.setApplications(new ArrayList<>());
        }
        if (post.getApplications()
                .stream()
                .anyMatch(applier -> Objects.equals(applier.getId(), currentUser.getId()))) {
            throw new InternalException(ResponseCode.POST_ERROR_EXISTED_APPLIER);
        }

        post.getApplications().add(currentUser);
        Post savedPost = postService.savePost(post);

        // Todo notify to poster
        pushNotificationService.sendTo(post.getAuthor().getId(), new NotificationDto()
                .setTitle(NotificationConstant.Title.POST_NEW_APPLICATION)
                .setBody(String.format(NotificationConstant.Body.POST_NEW_APPLICATION, currentUser.getName()))
                .setAction(NotificationAction.OPEN_POST)
                .setReferenceId(savedPost.getId().toString())
        );

        return new StatusResponse(savedPost != null);
    }
}
