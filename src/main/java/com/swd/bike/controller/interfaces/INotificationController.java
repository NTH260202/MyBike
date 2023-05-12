package com.swd.bike.controller.interfaces;

import com.swd.bike.config.OpenAPIConfig;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.notification.request.GetNotificationsRequest;
import com.swd.bike.dto.notification.response.NotificationResponse;
import com.swd.bike.dto.notification.response.NumOfUnreadNotificationsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/notifications")
@Tag(name = "Notifications Controller", description = "Thao tác với notifications")
@SecurityRequirement(name = OpenAPIConfig.BEARER_SCHEME)
public interface INotificationController {
    @GetMapping()
    @Operation(summary = "Get All Notifications")
    ResponseEntity<ResponseBase<PageResponse<NotificationResponse>>> getNotifications(@ParameterObject GetNotificationsRequest request);

    @PutMapping("/{id}/read")
    @Operation(summary = "Read Notification")
    ResponseEntity<ResponseBase<StatusResponse>> read(@PathVariable Long id);

    @PutMapping("/all/read")
    @Operation(summary = "Read Notification")
    ResponseEntity<ResponseBase<StatusResponse>> readAll();

    @PutMapping("/{id}/unread")
    @Operation(summary = "Unread Notification")
    ResponseEntity<ResponseBase<StatusResponse>> unread(@PathVariable Long id);

    @GetMapping("/count-unread")
    @Operation(summary = "Count num of unread notifications")
    ResponseEntity<ResponseBase<NumOfUnreadNotificationsResponse>> countNumOfUnread();
}
