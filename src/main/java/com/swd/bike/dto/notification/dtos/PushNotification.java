package com.swd.bike.dto.notification.dtos;

import com.swd.bike.enums.notification.NotificationAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PushNotification {
    private String title;
    private String text;
    private String url;
    private String icon;
    private LocalDateTime time;
    private NotificationAction action;
    private String objectId;
}
