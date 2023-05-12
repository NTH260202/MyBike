package com.swd.bike.controller;

import com.swd.bike.common.BaseConstant;
import com.swd.bike.controller.interfaces.ITestController;
import com.swd.bike.core.BaseController;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.message.UpdateLocationMessage;
import com.swd.bike.dto.message.UpdateLocationRequest;
import com.swd.bike.dto.notification.request.SendNotificationRequest;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Trip;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.kafka.KafkaProducer;
import com.swd.bike.service.ContextService;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController extends BaseController implements ITestController {
    private final KafkaProducer kafkaProducer;
    private final ContextService contextService;
    private final ITripService tripService;

    @Override
    public ResponseEntity<ResponseBase<StatusResponse>> send(SendNotificationRequest request) {
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<StatusResponse>> send(UpdateLocationRequest request) {
        Account account = contextService.getLoggedInUser();
        if (account == null) {
            log.error("Account not found");
            throw new InternalException(ResponseCode.ACCOUNT_NOT_FOUND);
        }
        Trip trip = tripService.getTrip(request.getTripId());
        if (trip == null) {
            log.error("Current trip not found");
            throw new InternalException(ResponseCode.TRIP_ERROR_NOT_FOUND);
        }
        if (request != null) {
            kafkaProducer.send(BaseConstant.KAFKA_CHANNEL_TRIP,
                    new UpdateLocationMessage()
                            .setTripId(trip.getId())
                            .setAccountId(account.getId())
                            .setLatitude(request.getLatitude())
                            .setLongitude(request.getLongitude()));
        }
        return ResponseEntity.ok(new ResponseBase<>(new StatusResponse(true)));
    }
}
