package com.swd.bike.handler.userPost;

import com.swd.bike.common.NotificationConstant;
import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.notification.dtos.NotificationDto;
import com.swd.bike.dto.userPost.request.AcceptApplicationRequest;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Post;
import com.swd.bike.entity.Trip;
import com.swd.bike.enums.PostStatus;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.enums.TripRole;
import com.swd.bike.enums.TripStatus;
import com.swd.bike.enums.notification.NotificationAction;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.ContextService;
import com.swd.bike.service.interfaces.IAccountService;
import com.swd.bike.service.interfaces.IPostService;
import com.swd.bike.service.interfaces.IPushNotificationService;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class AcceptApplicationHandler extends RequestHandler<AcceptApplicationRequest, StatusResponse> {

    private final IPostService postService;
    private final ITripService tripService;
    private final IAccountService accountService;
    private final ContextService contextService;
    private final IPushNotificationService pushNotificationService;

    @Override
    @Transactional
    public StatusResponse handle(AcceptApplicationRequest request) {
        Post post = postService.getPost(request.getPostId());
        if (!postService.isPostActive(post)) {
            throw new InternalException(ResponseCode.POST_ERROR_NOT_FOUND);
        }

        Account current = contextService.getLoggedInUser();
        if (!postService.isAuthor(current, post)) {
            log.error("Current user is not post's author");
            throw new InternalException(ResponseCode.FAILED);
        }

        Account acceptedApplier = accountService.getById(request.getApplierId());
        List<Account> applications = post.getApplications();
        if (applications == null) {
            applications = new ArrayList<>();
        }

        if (acceptedApplier == null || applications.stream().noneMatch(applier -> Objects.equals(applier.getId(), acceptedApplier.getId()))) {
            throw new InternalException(ResponseCode.POST_ERROR_NOT_EXISTED_APPLIER);
        }

        Account grabber;
        Account passenger;
        if (TripRole.GRABBER.equals(post.getRole())) {
            grabber = post.getAuthor();
            passenger = acceptedApplier;
        } else {
            grabber = acceptedApplier;
            passenger = post.getAuthor();
        }

        Trip trip = new Trip()
                .setGrabber(grabber)
                .setPassenger(passenger)
                .setStatus(TripStatus.CREATED)
                .setPostedStartTime(post.getStartTime())
                .setDescription(post.getDescription())
                .setPost(post)
                .setStartStation(post.getStartStation())
                .setEndStation(post.getEndStation());

        Trip savedTrip = tripService.save(trip);

        // Todo schedule reminder to remind coming trip
        tripService.scheduleRemindComingTrip(trip);

        post.setStatus(PostStatus.COMPLETED);
        Post savedPost = postService.savePost(post);

        if (savedTrip != null && savedPost != null) {
            // Todo notify for applier
            pushNotificationService.sendTo(acceptedApplier.getId(), new NotificationDto()
                    .setTitle(NotificationConstant.Title.POST_ACCEPT_APPLICATION)
                    .setBody(String.format(NotificationConstant.Body.POST_ACCEPT_APPLICATION, current.getName()))
                    .setAction(NotificationAction.OPEN_TRIP)
                    .setReferenceId(savedTrip.getId().toString())
            );

            return new StatusResponse(true);
        }

        throw new InternalException(ResponseCode.FAILED);
    }
}
