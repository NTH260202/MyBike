
package com.swd.bike.handler.notification;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.notification.request.ReadNotificationRequest;
import com.swd.bike.entity.Notification;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ReadNotificationHandler extends RequestHandler<ReadNotificationRequest, StatusResponse> {

    private final INotificationService notificationService;

    @Override
    public StatusResponse handle(ReadNotificationRequest request) {
        Notification notification = notificationService.getById(request.getId());
        if (notification == null) {
            throw new InternalException(ResponseCode.NOTIFICATION_NOT_FOUND);
        }
        notification.setIsRead(true)
                .setReadAt(LocalDateTime.now());
        return new StatusResponse(notificationService.save(notification) != null);
    }
}
