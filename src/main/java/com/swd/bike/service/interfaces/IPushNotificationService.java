
package com.swd.bike.service.interfaces;

import com.swd.bike.dto.notification.dtos.NotificationDto;

import java.util.List;

public interface IPushNotificationService {
    boolean sendPublic(NotificationDto notificationDto);

    boolean sendTo(String accountId, NotificationDto notificationDto);

    boolean sendAdmin(NotificationDto notificationDto);

    boolean sendToList(List<String> accountIds, NotificationDto notificationDto);
}
