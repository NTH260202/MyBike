package com.swd.bike.service;

import com.swd.bike.dto.notification.dtos.NotificationDto;
import com.swd.bike.service.interfaces.IExpoService;
import io.github.jav.exposerversdk.ExpoPushMessage;
import io.github.jav.exposerversdk.PushClient;
import io.github.jav.exposerversdk.PushClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class ExpoService implements IExpoService {

    private ExpoPushMessage expoPushMessage;
    private PushClient client;

    public ExpoService() {
        this.expoPushMessage = new ExpoPushMessage();
        try {
            this.client = new PushClient();
        } catch (PushClientException e) {
            log.error("Error when init push client: {}", e.getMessage());
        }
    }

    @Override
    public void sendList(List<String> tokens, NotificationDto notification) {
        expoPushMessage.setTo(tokens);
        expoPushMessage.setTitle(notification.getTitle());
        expoPushMessage.setBody(notification.getBody());

        List<ExpoPushMessage> expoPushMessages = new ArrayList<>();
        expoPushMessages.add(expoPushMessage);


        List<List<ExpoPushMessage>> chunks = client.chunkPushNotifications(expoPushMessages);

        for (List<ExpoPushMessage> chunk : chunks) {
            client.sendPushNotificationsAsync(chunk);
        }
    }

    @Override
    public void sendTo(String token, NotificationDto notification) {
        this.sendList(Collections.singletonList(token), notification);
    }
}
