package com.swd.bike.scheduler;

import com.swd.bike.common.BaseConstant;
import com.swd.bike.common.NotificationConstant;
import com.swd.bike.config.SpringContext;
import com.swd.bike.dto.notification.dtos.NotificationDto;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Trip;
import com.swd.bike.enums.TripStatus;
import com.swd.bike.enums.notification.NotificationAction;
import com.swd.bike.service.interfaces.IPushNotificationService;
import com.swd.bike.service.interfaces.ITripService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.transaction.Transactional;
import java.time.Instant;

@Slf4j
public class RemindComingTripTask extends ScheduledTask {
    private final Long tripId;

    private final ITripService tripService;

    private final PlatformTransactionManager transactionManager;
    private final IPushNotificationService pushNotificationService;

    public RemindComingTripTask(Long tripId, Instant time) {
        super(time);
        this.tripId = tripId;
        this.tripService = SpringContext.getBean(ITripService.class);
        this.transactionManager = SpringContext.getBean(PlatformTransactionManager.class);
        this.pushNotificationService = SpringContext.getBean(IPushNotificationService.class);
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

    private void doTask() {
        try {
            Trip trip = tripService.getTrip(tripId);
            if (trip == null || !TripStatus.CREATED.equals(trip.getStatus())) {
                return;
            }

            log.info("[SCHEDULING]: Start remind coming trip. ID: {}", trip.getId());
            Account grabber = trip.getGrabber();
            Account passenger = trip.getPassenger();

            //Todo notification
            pushNotificationService.sendTo(grabber.getId(), new NotificationDto()
                    .setTitle(NotificationConstant.Title.IN_COMING_TRIP)
                    .setBody(String.format(NotificationConstant.Body.IN_COMING_TRIP,
                            passenger.getName(),
                            trip.getStartStation().getName(),
                            trip.getEndStation().getName(),
                            BaseConstant.REMIND_TRIP_BEFORE_IN_MINUTES))
                    .setAction(NotificationAction.OPEN_TRIP)
                    .setReferenceId(tripId.toString())
            );

            pushNotificationService.sendTo(passenger.getId(), new NotificationDto()
                    .setTitle(NotificationConstant.Title.IN_COMING_TRIP)
                    .setBody(String.format(NotificationConstant.Body.IN_COMING_TRIP,
                            grabber.getName(),
                            trip.getStartStation().getName(),
                            trip.getEndStation().getName(),
                            BaseConstant.REMIND_TRIP_BEFORE_IN_MINUTES))
                    .setAction(NotificationAction.OPEN_TRIP)
                    .setReferenceId(tripId.toString())
            );


        } catch (Exception ex) {
            log.error("[SCHEDULING] Remind trip with ID {} error: {}", tripId, ex.getMessage());
        }
    }
}
