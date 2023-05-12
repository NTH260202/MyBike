package com.swd.bike.service;

import com.swd.bike.dto.notification.dtos.NotificationDto;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Notification;
import com.swd.bike.enums.notification.NotificationType;
import com.swd.bike.repository.AccountRepository;
import com.swd.bike.repository.NotificationRepository;
import com.swd.bike.service.interfaces.INotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {
    private final NotificationRepository notificationRepository;
    private final AccountRepository accountRepository;

    @Override
    public void savePublic(NotificationDto notificationDto) {
        accountRepository.findAll().forEach(account -> {
            notificationRepository.save(new Notification()
                    .setType(NotificationType.GLOBAL)
                    .setTitle(notificationDto.getTitle())
                    .setBody(notificationDto.getBody())
                    .setTime(LocalDateTime.now())
                    .setAccount(account));
        });
    }

    @Override
    public void saveUser(String accountId, NotificationDto notificationDto) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            return;
        }
        notificationRepository.save(new Notification()
                .setType(NotificationType.GLOBAL)
                .setTitle(notificationDto.getTitle())
                .setBody(notificationDto.getBody())
                .setTime(LocalDateTime.now())
                .setAccount(account));
    }

    @Override
    public Page<Notification> getAll(Specification<Notification> specification, Pageable pageable) {
        return notificationRepository.findAll(specification, pageable);
    }

    @Override
    public Notification getById(Long id) {
        return notificationRepository.findById(id).orElse(null);
    }

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllByAccountIdAndIsReadIsFalse(String accountId) {
        return notificationRepository.findAllByAccountIdAndIsReadIsFalse(accountId);
    }

    @Override
    public Long getNumOfUnread(String accountId) {
        return notificationRepository.countByAccountIdAndIsReadIsFalse(accountId);
    }
}
