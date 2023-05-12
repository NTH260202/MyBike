
package com.swd.bike.handler.notification;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.notification.request.GetNumOfUnreadNotificationsRequest;
import com.swd.bike.dto.notification.response.NumOfUnreadNotificationsResponse;
import com.swd.bike.service.interfaces.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetNumOfUnreadNotificationsHandler extends RequestHandler<GetNumOfUnreadNotificationsRequest, NumOfUnreadNotificationsResponse> {

    private final INotificationService notificationService;

    @Override
    public NumOfUnreadNotificationsResponse handle(GetNumOfUnreadNotificationsRequest request) {
        return new NumOfUnreadNotificationsResponse(notificationService.getNumOfUnread(request.getUserId()));
    }
}
