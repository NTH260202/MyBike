package com.swd.bike.handler.userPost;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.account.response.AccountResponse;
import com.swd.bike.dto.common.ListResponse;
import com.swd.bike.dto.userPost.request.GetAllAppliersRequest;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Post;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetAllAppliersHandler extends RequestHandler<GetAllAppliersRequest, ListResponse<AccountResponse>> {

    private final IPostService postService;

    @Override
    @Transactional
    public ListResponse<AccountResponse> handle(GetAllAppliersRequest request) {
        Post post = postService.getPost(request.getId());
        if (post == null) {
            throw new InternalException(ResponseCode.POST_ERROR_NOT_FOUND);
        }

        List<Account> appliers = post.getApplications();
        if (appliers == null) {
            appliers = new ArrayList<>();
        }

        ListResponse<AccountResponse> response = new ListResponse<>();
        response.setItems(appliers
                .stream()
                .map(AccountResponse::new)
                .collect(Collectors.toList()));

        return response;
    }
}
