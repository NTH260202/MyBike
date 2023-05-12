package com.swd.bike.handler.trip;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.trip.request.GetPageByFilterRequest;
import com.swd.bike.dto.trip.response.GetTripPageResponse;
import com.swd.bike.entity.Trip;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetTripPageByFilterHandler extends RequestHandler<GetPageByFilterRequest, PageResponse<GetTripPageResponse>> {
    private final ITripService tripService;

    @Override
    public PageResponse<GetTripPageResponse> handle(GetPageByFilterRequest request) {
        Page<Trip> trips = tripService.getTripPageByFilter(request.getSpecification(), request.getPageable());

        PageResponse<GetTripPageResponse> tripPageResponse = new PageResponse<>(trips);

        tripPageResponse.setItems(trips.getContent().stream()
                .map(trip -> this.convertEntityToDTO(trip))
                .collect(Collectors.toList()));

        return tripPageResponse;
    }

    private GetTripPageResponse convertEntityToDTO(Trip trip) {
        return GetTripPageResponse.builder()
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
                .startStationId(trip.getStartStation().getId())
                .endStationId(trip.getEndStation().getId())
                .postedStartTime(trip.getPostedStartTime())
                .build();
    }
}
