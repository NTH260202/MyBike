package com.swd.bike.scheduler;

import com.swd.bike.common.NotificationConstant;
import com.swd.bike.config.SpringContext;
import com.swd.bike.dto.notification.dtos.NotificationDto;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Post;
import com.swd.bike.enums.PostStatus;
import com.swd.bike.enums.notification.NotificationAction;
import com.swd.bike.service.interfaces.IPostService;
import com.swd.bike.service.interfaces.IPushNotificationService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;

@Slf4j
public class ClearExpiredPostTask extends ScheduledTask {

    private final Long postId;
    private final IPostService postService;
    private final IPushNotificationService pushNotificationService;
    private final PlatformTransactionManager transactionManager;

    public ClearExpiredPostTask(Long postId, Instant time) {
        super(time);
        this.postId = postId;
        postService = SpringContext.getBean(IPostService.class);
        pushNotificationService = SpringContext.getBean(IPushNotificationService.class);
        transactionManager = SpringContext.getBean(PlatformTransactionManager.class);
    }

    @Override
    @Transactional
    public void run() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                doTask();
            }
        });
    }

    public void doTask() {
        try {
            Post post = postService.getPost(postId);
            if (post == null
                    || PostStatus.COMPLETED.equals(post.getStatus())
                    || post.getStartTime().isAfter(LocalDateTime.now())) {
                return;
            }

            log.info("[SCHEDULING] Clear post. ID: {}", post.getId());

            post.setStatus(PostStatus.COMPLETED);
            Post savedPost = postService.savePost(post);

            // Todo Notification
            Account account = post.getAuthor();
            pushNotificationService.sendTo(account.getId(), new NotificationDto()
                    .setTitle(NotificationConstant.Title.POST_EXPIRED)
                    .setBody(String.format(NotificationConstant.Body.POST_EXPIRED, account.getName()))
                    .setAction(NotificationAction.OPEN_POST)
                    .setReferenceId(savedPost.getId().toString())
            );

        } catch (Exception ex) {
            log.error("Clear post with ID {} failed: {}", postId, ex.getMessage());
        }

    }
}
