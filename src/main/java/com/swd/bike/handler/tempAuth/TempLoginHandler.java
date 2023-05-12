package com.swd.bike.handler.tempAuth;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.auth.response.AccessTokenResponseCustom;
import com.swd.bike.dto.tempAuth.TempLoginRequest;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TempLoginHandler extends RequestHandler<TempLoginRequest, AccessTokenResponseCustom> {
    private final KeycloakService keycloakService;
    @Override
    public AccessTokenResponseCustom handle(TempLoginRequest request) {
        AccessTokenResponseCustom token = keycloakService.getUserJWT(request.getEmail(), request.getPassword());
        if (token == null) {
            throw new InternalException(ResponseCode.INVALID_USERNAME_OR_PASSWORD);
        }
        return token;
    }
}
