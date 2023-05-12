package com.swd.bike.handler.auth;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.auth.request.UsernamePasswordLoginRequest;
import com.swd.bike.dto.auth.response.AccessTokenResponseCustom;
import com.swd.bike.service.interfaces.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsernamePasswordLoginHandler extends RequestHandler<UsernamePasswordLoginRequest, AccessTokenResponseCustom> {
    private final IAuthService authService;

    @Override
    public AccessTokenResponseCustom handle(UsernamePasswordLoginRequest request) {
        return authService.loginByUsernameAndPassword(request.getUsername(), request.getPassword());
    }
}
