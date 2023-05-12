package com.swd.bike.handler.trip;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.trip.request.GetTripDetailRequest;
import com.swd.bike.dto.trip.response.GetTripDetailResponse;
import com.swd.bike.entity.Trip;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetDetailTripHandler extends RequestHandler<GetTripDetailRequest, GetTripDetailResponse> {
    private final ITripService tripService;
    @Override
    @Transactional
    public GetTripDetailResponse handle(GetTripDetailRequest request) {
        Trip trip = tripService.getDetailById(request.getId());
        return GetTripDetailResponse.builder()
                .id(trip.getId())
                .passengerId(trip.getPassenger().getId())
                .passengerName(trip.getPassenger().getName())
                .grabberId(trip.getGrabber().getId())
                .grabberName(trip.getGrabber().getName())
                .startTime(trip.getStartAt() != null
                        ? OffsetDateTime.of(trip.getStartAt(), ZoneOffset.UTC).toString()
                        : null)
                .endTime(trip.getFinishAt() != null
                        ? OffsetDateTime.of(trip.getFinishAt(), ZoneOffset.UTC).toString()
                        : null)
                .cancelTime(trip.getCancelAt() != null
                        ? OffsetDateTime.of(trip.getCancelAt(), ZoneOffset.UTC).toString()
                        : null)
                .feedbackPoint(trip.getFeedbackPoint())
                .status(trip.getStatus())
                .startStationId(trip.getStartStation().getId())
                .startStationName(trip.getStartStation().getName())
                .endStationId(trip.getEndStation().getId())
                .endStationName(trip.getEndStation().getName())
                .postedStartTime(trip.getPostedStartTime())
                .build();
    }
}
