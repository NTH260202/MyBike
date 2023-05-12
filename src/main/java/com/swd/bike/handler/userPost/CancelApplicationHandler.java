package com.swd.bike.handler.userPost;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.userPost.request.CancelApplicationRequest;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Post;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.ContextService;
import com.swd.bike.service.interfaces.IPostService;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CancelApplicationHandler extends RequestHandler<CancelApplicationRequest, StatusResponse> {

    private final IPostService postService;
    private final ITripService tripService;
    private final ContextService contextService;

    @Override
    @Transactional
    public StatusResponse handle(CancelApplicationRequest request) {
        Post post = postService.getPost(request.getPostId());
        if (!postService.isPostActive(post)) {
            throw new InternalException(ResponseCode.POST_ERROR_NOT_FOUND);
        }

        Account currentUser = contextService.getLoggedInUser();

        List<Account> applications = post.getApplications();
        if (applications == null || applications.stream().noneMatch(applier -> Objects.equals(applier.getId(), currentUser.getId()))) {
            throw new InternalException(ResponseCode.POST_ERROR_NOT_EXISTED_APPLIER);
        }

        applications = applications.stream()
                .filter(applier -> !Objects.equals(applier.getId(), currentUser.getId()))
                .collect(Collectors.toList());

        post.setApplications(applications);
        Post savedPost = postService.savePost(post);
        return new StatusResponse(savedPost != null);
    }
}
