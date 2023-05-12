package com.swd.bike.handler.userTrip;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.userTrip.request.GetTripRequest;
import com.swd.bike.dto.userTrip.response.TripDetailResponse;
import com.swd.bike.entity.Trip;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class GetTripHandler extends RequestHandler<GetTripRequest, TripDetailResponse> {

    private final ITripService tripService;

    @Override
    @Transactional
    public TripDetailResponse handle(GetTripRequest request) {
        Trip trip = tripService.getTrip(request.getId());
        if (trip == null) {
            throw new InternalException(ResponseCode.TRIP_ERROR_NOT_FOUND);
        }
        return new TripDetailResponse(trip);
    }
}
