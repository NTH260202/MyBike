package com.swd.bike.handler.userPost;

import com.swd.bike.common.NotificationConstant;
import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.notification.dtos.NotificationDto;
import com.swd.bike.dto.userPost.request.DeletePostRequest;
import com.swd.bike.dto.userPost.response.PostResponse;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Post;
import com.swd.bike.enums.PostStatus;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.enums.notification.NotificationAction;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.ContextService;
import com.swd.bike.service.interfaces.IPostService;
import com.swd.bike.service.interfaces.IPushNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeletePostHandler extends RequestHandler<DeletePostRequest, PostResponse> {

    private final IPostService postService;
    private final ContextService contextService;
    private final IPushNotificationService pushNotificationService;

    @Override
    @Transactional
    public PostResponse handle(DeletePostRequest request) {
        Post post = postService.getPost(request.getId());
        if (!postService.isPostActive(post)) {
            throw new InternalException(ResponseCode.POST_ERROR_NOT_FOUND);
        }

        if (!postService.isAuthor(contextService.getLoggedInUser(), post)) {
            log.error("Current user is not post's author");
            throw new InternalException(ResponseCode.FAILED);
        }

        post.setStatus(PostStatus.COMPLETED);
        Post savedPost = postService.savePost(post);

        Account author = post.getAuthor();
        List<Account> postApplications = post.getApplications();
        if (!CollectionUtils.isEmpty(postApplications)) {
            // Todo notify to all applier
            postApplications.forEach(applier -> {
                pushNotificationService.sendTo(applier.getId(), new NotificationDto()
                        .setTitle(NotificationConstant.Title.POST_DELETED)
                        .setBody(String.format(NotificationConstant.Body.POST_DELETED, author.getName()))
                        .setAction(NotificationAction.OPEN_POST)
                        .setReferenceId(savedPost.getId().toString())
                );
            });
        }

        return new PostResponse(savedPost);
    }
}
