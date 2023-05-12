package com.swd.bike.dto.notification.dtos;

import com.swd.bike.dto.kafka.KafkaMessage;
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
public class NotificationMessage extends KafkaMessage {
    private String accountId;
    private String title;
    private String body;
    private NotificationAction action;
    private String referenceId;

    public NotificationMessage(String accountId, NotificationDto notificationDto) {
        this.accountId = accountId;
        this.title = notificationDto.getTitle();
        this.body = notificationDto.getBody();
        this.action = notificationDto.getAction();
        this.referenceId = notificationDto.getReferenceId();
    }
}
