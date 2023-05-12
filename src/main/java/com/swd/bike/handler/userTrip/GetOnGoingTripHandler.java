package com.swd.bike.handler.userTrip;

import com.swd.bike.core.BaseRequestData;
import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.userTrip.request.GetOnGoingTripRequest;
import com.swd.bike.dto.userTrip.response.TripDetailResponse;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Trip;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.ContextService;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class GetOnGoingTripHandler extends RequestHandler<GetOnGoingTripRequest, TripDetailResponse> {

    private final ITripService tripService;

    private final ContextService contextService;

    @Override
    @Transactional
    public TripDetailResponse handle(GetOnGoingTripRequest request) {
        Account currentUser = contextService.getLoggedInUser();
        if (currentUser == null) {
            throw new InternalException(ResponseCode.UNAUTHORIZED_REQUEST);
        }

        Trip onGoingTrip = tripService.getCurrentTrip(currentUser);
        if (onGoingTrip != null) {
            return new TripDetailResponse(onGoingTrip);
        }

        return null;
    }
}
