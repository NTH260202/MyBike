
package com.swd.bike.handler.notification;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.notification.request.UnreadNotificationRequest;
import com.swd.bike.entity.Notification;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnreadNotificationHandler extends RequestHandler<UnreadNotificationRequest, StatusResponse> {

    private final INotificationService notificationService;

    @Override
    public StatusResponse handle(UnreadNotificationRequest request) {
        Notification notification = notificationService.getById(request.getId());
        if (notification == null) {
            throw new InternalException(ResponseCode.NOTIFICATION_NOT_FOUND);
        }
        notification.setIsRead(false)
                .setReadAt(null);
        return new StatusResponse(notificationService.save(notification) != null);
    }
}
