package com.swd.bike.handler.userPost;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.userPost.request.GetPostRequest;
import com.swd.bike.dto.userPost.response.PostDetailResponse;
import com.swd.bike.entity.Post;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class GetPostHandler extends RequestHandler<GetPostRequest, PostDetailResponse> {

    private final IPostService postService;

    @Override
    @Transactional
    public PostDetailResponse handle(GetPostRequest request) {
        Post post = postService.getPost(request.getPostId());
        if (post == null) {
            throw new InternalException(ResponseCode.POST_ERROR_NOT_FOUND);
        }
        return new PostDetailResponse(post);
    }
}