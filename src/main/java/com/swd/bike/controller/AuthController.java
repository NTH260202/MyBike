package com.swd.bike.controller;

import com.swd.bike.controller.interfaces.IAuthController;
import com.swd.bike.core.BaseController;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.auth.request.GoogleLoginRequest;
import com.swd.bike.dto.auth.request.LogoutRequest;
import com.swd.bike.dto.auth.request.RefreshTokenRequest;
import com.swd.bike.dto.auth.request.UsernamePasswordLoginRequest;
import com.swd.bike.dto.auth.response.AccessTokenResponseCustom;
import com.swd.bike.dto.common.StatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController extends BaseController implements IAuthController {

    @Override
    public ResponseEntity<ResponseBase<AccessTokenResponseCustom>> login(UsernamePasswordLoginRequest request) {
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<AccessTokenResponseCustom>> loginWithGoogle(GoogleLoginRequest request) {
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<StatusResponse>> logout(LogoutRequest request) {
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<AccessTokenResponseCustom>> logout(RefreshTokenRequest request) {
        return this.execute(request);
    }
}
