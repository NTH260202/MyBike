package com.swd.bike.service.interfaces;

import com.swd.bike.dto.notification.dtos.NotificationDto;
import com.swd.bike.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface INotificationService {

    void savePublic(NotificationDto notificationDto);

    void saveUser(String accountId, NotificationDto notificationDto);

    Page<Notification> getAll(Specification<Notification> specification, Pageable pageable);

    Notification getById(Long id);

    Notification save(Notification notification);

    List<Notification> getAllByAccountIdAndIsReadIsFalse(String accountId);

    Long getNumOfUnread(String accountId);
}
