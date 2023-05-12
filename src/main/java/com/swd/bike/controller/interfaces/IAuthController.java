package com.swd.bike.controller.interfaces;

import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.auth.request.GoogleLoginRequest;
import com.swd.bike.dto.auth.request.LogoutRequest;
import com.swd.bike.dto.auth.request.RefreshTokenRequest;
import com.swd.bike.dto.auth.request.UsernamePasswordLoginRequest;
import com.swd.bike.dto.auth.response.AccessTokenResponseCustom;
import com.swd.bike.dto.common.StatusResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Tag(name = "Auth Controller", description = "Thao tác với auth")
@RequestMapping(value = "/api/v1/auth")
public interface IAuthController {

    @Operation(
            summary = "Login",
            description = "- login với username và password"
    )
    @PostMapping("/login")
    ResponseEntity<ResponseBase<AccessTokenResponseCustom>> login(@Valid @RequestBody UsernamePasswordLoginRequest request);

    @Operation(
            summary = "Login with Google",
            description = "- Login với Google"
    )
    @PostMapping("/google/login")
    ResponseEntity<ResponseBase<AccessTokenResponseCustom>> loginWithGoogle(@Valid @RequestBody GoogleLoginRequest request);

    @Operation(
            summary = "Logout",
            description = "- Logout"
    )
    @PostMapping("/logout")
    ResponseEntity<ResponseBase<StatusResponse>> logout(@Valid @RequestBody LogoutRequest request);

    @Operation(
            summary = "Refresh Token",
            description = "- Refresh Token"
    )
    @PostMapping("/refresh")
    ResponseEntity<ResponseBase<AccessTokenResponseCustom>> logout(@Valid @RequestBody RefreshTokenRequest request);

}
