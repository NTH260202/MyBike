package com.swd.bike.service.interfaces;

import com.swd.bike.dto.notification.dtos.NotificationDto;

import java.util.List;

public interface IExpoService {
    void sendList(List<String> token, NotificationDto notification);

    void sendTo(String token, NotificationDto notification);
}
