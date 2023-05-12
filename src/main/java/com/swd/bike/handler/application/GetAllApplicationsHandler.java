package com.swd.bike.handler.application;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.application.request.GetAllApplicationsRequest;
import com.swd.bike.dto.application.response.AppliedPostResponse;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Post;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.ContextService;
import com.swd.bike.service.interfaces.IPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class GetAllApplicationsHandler extends RequestHandler<GetAllApplicationsRequest, PageResponse<AppliedPostResponse>> {

    private final IPostService postService;
    private final ContextService contextService;

    @Override
    @Transactional
    public PageResponse<AppliedPostResponse> handle(GetAllApplicationsRequest request) {
       try {
           Account currentAccount = contextService.getLoggedInUser();
           Page<Post> pageResult = postService.getAppliedPost(currentAccount, request.getPageable());
           PageResponse<AppliedPostResponse> response = new PageResponse<>(pageResult);
           response.setItems(pageResult.getContent()
                   .stream()
                   .map(post -> new AppliedPostResponse(post, currentAccount))
                   .collect(Collectors.toList()));

           return response;
       } catch (Exception exception) {
           log.error("Failed to get all post applications, {}", exception.getMessage());
       }
       throw new InternalException(ResponseCode.FAILED);
    }
}
