package com.swd.bike.kafka;


import com.swd.bike.common.BaseConstant;
import com.swd.bike.dto.message.UpdateLocationMessage;
import com.swd.bike.dto.message.UpdateLocationResponse;
import com.swd.bike.dto.notification.dtos.NotificationDto;
import com.swd.bike.dto.notification.dtos.NotificationMessage;
import com.swd.bike.entity.Trip;
import com.swd.bike.service.ExpoService;
import com.swd.bike.service.ExponentPushTokenService;
import com.swd.bike.service.interfaces.INotificationService;
import com.swd.bike.service.interfaces.ITripService;
import com.swd.bike.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private final SimpMessagingTemplate template;
    private final ExponentPushTokenService exponentPushTokenService;
    private final ExpoService expoService;
    private final INotificationService notificationService;
    private final ITripService tripService;

    @KafkaListener(topics = BaseConstant.KAFKA_CHANNEL_PUBLIC, groupId = BaseConstant.KAFKA_GROUP_ID)
    public void consumeNotificationPublic(String message) {
        NotificationMessage notification = JsonUtil.INSTANCE.getObject(message, NotificationMessage.class);
        if (notification == null) {
            log.error("Send notification failed.");
            return;
        }
        log.info("Received public message: " + message);
        // Push notification to public
        this.pushPublic(new NotificationDto(notification));
    }

    @KafkaListener(topics = BaseConstant.KAFKA_CHANNEL_USER, groupId = BaseConstant.KAFKA_GROUP_ID)
    public void consumeNotificationPrivate(String message) {
        NotificationMessage notification = JsonUtil.INSTANCE.getObject(message, NotificationMessage.class);
        if (notification == null) {
            log.error("Send notification failed.");
            return;
        }
        log.info("Received user message: " + message);

        // Push notification to user
        this.pushUser(notification.getAccountId(), new NotificationDto(notification));
    }

    @KafkaListener(topics = BaseConstant.KAFKA_CHANNEL_TRIP, groupId = BaseConstant.KAFKA_GROUP_ID)
    public void consumeLocation(String message) {
        log.info("Received update location message: " + message);

        UpdateLocationMessage updateLocationMessage = JsonUtil.INSTANCE.getObject(message, UpdateLocationMessage.class);
        if (updateLocationMessage == null) {
            log.error("Update location failed.");
            return;
        }

        Trip trip = tripService.getTrip(updateLocationMessage.getTripId());
        if (trip == null) {
            log.error("trip not found.");
            return;
        }

        template.convertAndSend(String.format("/trip/%s/location", trip.getId()), new UpdateLocationResponse(updateLocationMessage));
    }

    @Async
    void pushPublic(NotificationDto notificationDto) {
        // Send web socket
        template.convertAndSend("/all/notifications", notificationDto);

        // Send expo
        expoService.sendList(exponentPushTokenService.getAllTokens(), notificationDto);

        // Save notification
        notificationService.savePublic(notificationDto);
    }

    @Async
    void pushUser(String accountId, NotificationDto notificationDto) {
        // Send web socket
        template.convertAndSendToUser(accountId, "notifications", notificationDto);

        // Send expo
        expoService.sendList(exponentPushTokenService.getAllTokensByAccountId(accountId), notificationDto);

        // Save notification
        notificationService.saveUser(accountId, notificationDto);
    }

}

