package com.swd.bike.controller.interfaces;

import com.swd.bike.config.OpenAPIConfig;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.token.request.AddExpoTokenRequest;
import com.swd.bike.dto.token.request.DeleteExpoTokenRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Exponent Push Token Controller", description = "Thao tác với push tokens")
@RequestMapping(value = "/api/v1/expo-tokens")
@SecurityRequirement(name = OpenAPIConfig.BEARER_SCHEME)
public interface IExponentPushTokenController {

    @Operation(summary = "Add Expo push token")
    @PostMapping()
    ResponseEntity<ResponseBase<StatusResponse>> addToken(@RequestBody AddExpoTokenRequest request);

    @Operation(summary = "Delete Expo push token")
    @PutMapping()
    ResponseEntity<ResponseBase<StatusResponse>> deleteToken(@RequestBody DeleteExpoTokenRequest request);

}
