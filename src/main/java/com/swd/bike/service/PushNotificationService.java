package com.swd.bike.service;

import com.swd.bike.common.BaseConstant;
import com.swd.bike.dto.notification.dtos.NotificationDto;
import com.swd.bike.dto.notification.dtos.NotificationMessage;
import com.swd.bike.enums.Role;
import com.swd.bike.kafka.KafkaProducer;
import com.swd.bike.repository.AccountRepository;
import com.swd.bike.repository.NotificationRepository;
import com.swd.bike.service.interfaces.IPushNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushNotificationService implements IPushNotificationService {
    private final NotificationRepository notificationRepository;
    private final KafkaProducer kafkaProducer;
    private final AccountRepository accountRepository;

    @Async
    boolean send(String topic, NotificationMessage notification) {
        try {
            if (notification == null || topic == null) {
                log.error("Send notification failed.");
                return false;
            }
            return kafkaProducer.send(topic, notification);
        } catch (Exception ex) {
            log.error("Send notification failed {}", ex.getMessage());
        }
        return false;
    }


    @Override
    public boolean sendPublic(NotificationDto notificationDto) {
        log.info("Send public message to topic: {}", BaseConstant.KAFKA_CHANNEL_PUBLIC);
        return this.send(BaseConstant.KAFKA_CHANNEL_PUBLIC, new NotificationMessage(null, notificationDto));
    }

    @Override
    public boolean sendTo(String accountId, NotificationDto notificationDto) {
        log.info("Send message to topic: {}", String.format(BaseConstant.KAFKA_CHANNEL_USER, accountId));
        return this.send(BaseConstant.KAFKA_CHANNEL_USER, new NotificationMessage(accountId, notificationDto));
    }

    @Override
    public boolean sendAdmin(NotificationDto notificationDto) {
        return sendToList(accountRepository.findAllIdsByRoleIs(Role.ADMIN), notificationDto);
    }

    @Override
    public boolean sendToList(List<String> accountIds, NotificationDto notificationDto) {
        return accountIds.stream()
                .filter(accountId -> !this.send(BaseConstant.KAFKA_CHANNEL_USER, new NotificationMessage(accountId, notificationDto)))
                .collect(Collectors.toList()).size() == 0;
    }
}
