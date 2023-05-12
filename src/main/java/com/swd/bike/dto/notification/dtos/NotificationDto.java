package com.swd.bike.dto.notification.dtos;

import com.swd.bike.enums.notification.NotificationAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class NotificationDto {
    private String title;
    private String body;
    private NotificationAction action;
    private String referenceId;

    public NotificationDto(NotificationMessage notificationMessage) {
        this.title = notificationMessage.getTitle();
        this.body = notificationMessage.getBody();
        this.action = notificationMessage.getAction();
        this.referenceId = notificationMessage.getReferenceId();
    }
}
