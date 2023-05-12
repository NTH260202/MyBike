package com.swd.bike.handler.token;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.token.request.AddExpoTokenRequest;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.ExponentPushToken;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IAccountService;
import com.swd.bike.service.interfaces.IExponentPushTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddExpoTokenHandler extends RequestHandler<AddExpoTokenRequest, StatusResponse> {

    private final IExponentPushTokenService exponentPushTokenService;
    private final IAccountService accountService;

    @Override
    public StatusResponse handle(AddExpoTokenRequest request) {
        Account account = accountService.getById(request.getUserId());
        if (account == null) {
            throw new InternalException(ResponseCode.ACCOUNT_NOT_FOUND);
        }

        return new StatusResponse(exponentPushTokenService
                .save(new ExponentPushToken()
                        .setToken(request.getToken())
                        .setAccount(account)) != null);
    }
}
