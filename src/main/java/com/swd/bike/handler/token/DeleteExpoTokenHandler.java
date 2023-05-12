package com.swd.bike.handler.token;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.token.request.DeleteExpoTokenRequest;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.ExponentPushToken;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IAccountService;
import com.swd.bike.service.interfaces.IExponentPushTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DeleteExpoTokenHandler extends RequestHandler<DeleteExpoTokenRequest, StatusResponse> {

    private final IExponentPushTokenService exponentPushTokenService;
    private final IAccountService accountService;

    @Override
    public StatusResponse handle(DeleteExpoTokenRequest request) {
        Account account = accountService.getById(request.getUserId());
        if (account == null) {
            throw new InternalException(ResponseCode.ACCOUNT_NOT_FOUND);
        }
        ExponentPushToken token = exponentPushTokenService.getByToken(request.getToken());
        if (token == null || !Objects.equals(token.getAccount(), account)) {
            return new StatusResponse(false);
        }
        exponentPushTokenService.delete(token);
        return new StatusResponse(true);
    }
}
