package com.swd.bike.handler.user;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.user.request.GetUserDetailRequest;
import com.swd.bike.dto.user.response.UserDetailResponse;
import com.swd.bike.entity.Account;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserDetailHandler extends RequestHandler<GetUserDetailRequest, UserDetailResponse> {
    private final IAccountService accountService;

    @Override
    public UserDetailResponse handle(GetUserDetailRequest request) {
        Account account = accountService.getById(request.getId());
        if (account == null) {
            throw new InternalException(ResponseCode.USER_NOT_FOUND);
        }
        return UserDetailResponse.convert(account);
    }
}
