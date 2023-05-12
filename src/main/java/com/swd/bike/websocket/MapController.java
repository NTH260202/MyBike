package com.swd.bike.websocket;

import com.swd.bike.common.BaseConstant;
import com.swd.bike.dto.message.UpdateLocationMessage;
import com.swd.bike.dto.message.UpdateLocationRequest;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Trip;
import com.swd.bike.kafka.KafkaProducer;
import com.swd.bike.service.ContextService;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MapController {
    private final KafkaProducer kafkaProducer;
    private final ContextService contextService;
    private final ITripService tripService;

    @MessageMapping("/update-location")
    public void updateLocation(@Payload UpdateLocationRequest request, @Header(HttpHeaders.AUTHORIZATION) String token) {
        if (request == null || token == null) {
            log.error("Authorization fail");
            return;
        }
        Account account = contextService.getLoggedInUser(token.replace("Bearer ", ""));
        if (account == null) {
            log.error("Account not found");
            return;
        }
        Trip trip = tripService.getTrip(request.getTripId());
        if (trip == null) {
            log.error("Trip not found");
            return;
        }

        kafkaProducer.send(BaseConstant.KAFKA_CHANNEL_TRIP,
                new UpdateLocationMessage()
                        .setTripId(trip.getId())
                        .setAccountId(account.getId())
                        .setLatitude(request.getLatitude())
                        .setLongitude(request.getLongitude()));
    }
}
