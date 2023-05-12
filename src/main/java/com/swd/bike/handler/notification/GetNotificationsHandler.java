
package com.swd.bike.handler.notification;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.notification.request.GetNotificationsRequest;
import com.swd.bike.dto.notification.response.NotificationResponse;
import com.swd.bike.entity.Notification;
import com.swd.bike.service.interfaces.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetNotificationsHandler extends RequestHandler<GetNotificationsRequest, PageResponse<NotificationResponse>> {

    private final INotificationService notificationService;

    @Override
    public PageResponse<NotificationResponse> handle(GetNotificationsRequest request) {
        Page<Notification> notifications = notificationService.getAll(request.getSpecification(), request.getPageable());
        PageResponse<NotificationResponse> response = new PageResponse<>(notifications);

        response.setItems(notifications
                .getContent()
                .stream()
                .map(NotificationResponse::convert)
                .collect(Collectors.toList()));

        return response;
    }
}
