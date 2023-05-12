
package com.swd.bike.handler.notification;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.notification.request.ReadAllNotificationsRequest;
import com.swd.bike.entity.Notification;
import com.swd.bike.service.interfaces.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReadAllNotificationsHandler extends RequestHandler<ReadAllNotificationsRequest, StatusResponse> {

    private final INotificationService notificationService;

    @Override
    public StatusResponse handle(ReadAllNotificationsRequest request) {
        List<Notification> notifications = notificationService.getAllByAccountIdAndIsReadIsFalse(request.getUserId());
        if (notifications != null) {
            notifications.forEach(notification ->
                    notificationService.save(notification.setIsRead(true)
                            .setReadAt(LocalDateTime.now()))
            );
        }
        return new StatusResponse(true);
    }
}
