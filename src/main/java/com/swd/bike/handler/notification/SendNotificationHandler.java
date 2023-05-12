
package com.swd.bike.handler.notification;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.notification.dtos.NotificationDto;
import com.swd.bike.dto.notification.request.SendNotificationRequest;
import com.swd.bike.service.interfaces.IPushNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendNotificationHandler extends RequestHandler<SendNotificationRequest, StatusResponse> {

    private final IPushNotificationService pushNotificationService;

    @Override
    public StatusResponse handle(SendNotificationRequest request) {
        NotificationDto notificationDto = new NotificationDto()
                .setTitle(request.getTitle())
                .setBody(request.getBody())
                .setAction(request.getAction())
                .setReferenceId(request.getReferenceId());
        if (request.getTo() == null) {
            pushNotificationService.sendPublic(notificationDto);
        } else {
            pushNotificationService.sendTo(request.getTo(), notificationDto);
        }
        return new StatusResponse();
    }
}
