package com.swd.bike.handler.userPost;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.userPost.request.GetActivePostsRequest;
import com.swd.bike.dto.userPost.response.PostResponse;
import com.swd.bike.entity.Post;
import com.swd.bike.service.interfaces.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetActivePostsHandler extends RequestHandler<GetActivePostsRequest, PageResponse<PostResponse>> {

    private final IPostService postService;

    @Override
    @Transactional
    @Cacheable(value = "pagePosts", key = "#request.toString()", condition = "#request != null")
    public PageResponse<PostResponse> handle(GetActivePostsRequest request) {
        Page<Post> pageResult = postService.getAllPosts(request.getSpecification(), request.getPageable());
        PageResponse<PostResponse> response = new PageResponse<>(pageResult);
        response.setItems(pageResult
                .getContent()
                .stream()
                .map(PostResponse::new)
                .collect(Collectors.toList()));
        return response;
    }
}
