package com.swd.bike.controller;

import com.swd.bike.controller.interfaces.INotificationController;
import com.swd.bike.core.BaseController;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.notification.request.*;
import com.swd.bike.dto.notification.response.NotificationResponse;
import com.swd.bike.dto.notification.response.NumOfUnreadNotificationsResponse;
import com.swd.bike.entity.Notification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController extends BaseController implements INotificationController {


    @Override
    public ResponseEntity<ResponseBase<PageResponse<NotificationResponse>>> getNotifications(GetNotificationsRequest request) {
        request.setUserId(this.getUserId());
        request.setSortBy(Notification.Fields.time);
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<StatusResponse>> read(Long id) {
        ReadNotificationRequest request = new ReadNotificationRequest(id);
        request.setUserId(this.getUserId());
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<StatusResponse>> readAll() {
        ReadAllNotificationsRequest request = new ReadAllNotificationsRequest();
        request.setUserId(this.getUserId());
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<StatusResponse>> unread(Long id) {
        UnreadNotificationRequest request = new UnreadNotificationRequest(id);
        request.setUserId(this.getUserId());
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<NumOfUnreadNotificationsResponse>> countNumOfUnread() {
        GetNumOfUnreadNotificationsRequest request = new GetNumOfUnreadNotificationsRequest();
        request.setUserId(this.getUserId());
        return this.execute(request);
    }
}
