package com.swd.bike.dto.notification.request;

import com.swd.bike.core.BaseRequestData;
import com.swd.bike.enums.notification.NotificationAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendNotificationRequest extends BaseRequestData {
    private String to;
    private String title;
    private String body;
    private NotificationAction action;
    private String referenceId;
}
