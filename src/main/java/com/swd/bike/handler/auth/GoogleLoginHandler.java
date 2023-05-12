package com.swd.bike.handler.auth;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.auth.request.GoogleLoginRequest;
import com.swd.bike.dto.auth.response.AccessTokenResponseCustom;
import com.swd.bike.service.interfaces.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleLoginHandler extends RequestHandler<GoogleLoginRequest, AccessTokenResponseCustom> {
    private final IAuthService authService;

    @Override
    public AccessTokenResponseCustom handle(GoogleLoginRequest request) {
        return authService.loginByGoogle(request.getCode(), request.getRedirectUri());
    }
}
