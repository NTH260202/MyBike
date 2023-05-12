package com.swd.bike.controller.interfaces;

import com.swd.bike.config.OpenAPIConfig;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.message.UpdateLocationRequest;
import com.swd.bike.dto.notification.request.SendNotificationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/public/test")
@Tag(name = "[Test] Test Controller", description = "Test controllet")
public interface ITestController {
    @PostMapping("/send-notification")
    @Operation(summary = "Push Notification")
    ResponseEntity<ResponseBase<StatusResponse>> send(@RequestBody SendNotificationRequest request);

    @PostMapping("/update-location")
    @Operation(summary = "UpdateLocation")
    @SecurityRequirement(name = OpenAPIConfig.BEARER_SCHEME)
    ResponseEntity<ResponseBase<StatusResponse>> send(@RequestBody UpdateLocationRequest request);
}
