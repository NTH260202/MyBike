package com.swd.bike.dto.notification.response;

import com.swd.bike.core.BaseResponseData;
import com.swd.bike.entity.Notification;
import com.swd.bike.enums.notification.NotificationType;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Accessors(chain = true)
public class NotificationResponse extends BaseResponseData {
    private Long id;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    private String title;
    private String body;
    private LocalDateTime time;
    private Boolean isRead;

    private LocalDateTime readAt;

    public static NotificationResponse convert(Notification notification) {
        if (notification == null) {
            return null;
        }
        return new NotificationResponse()
                .setId(notification.getId())
                .setType(notification.getType())
                .setTitle(notification.getTitle())
                .setBody(notification.getBody())
                .setTime(notification.getTime())
                .setIsRead(notification.getIsRead())
                .setReadAt(notification.getReadAt());
    }
}
