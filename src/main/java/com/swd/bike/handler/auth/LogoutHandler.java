package com.swd.bike.handler.auth;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.auth.request.LogoutRequest;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.service.interfaces.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogoutHandler extends RequestHandler<LogoutRequest, StatusResponse> {
    private final IAuthService authService;

    @Override
    public StatusResponse handle(LogoutRequest request) {
        authService.logout(request.getRefreshToken());
        return new StatusResponse(true);
    }
}
