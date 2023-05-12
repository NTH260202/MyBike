package com.swd.bike.controller;

import com.swd.bike.controller.interfaces.IUserTripController;
import com.swd.bike.core.BaseController;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.userTrip.request.*;
import com.swd.bike.dto.userTrip.response.TripDetailResponse;
import com.swd.bike.dto.userTrip.response.TripResponse;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.enums.TripAction;
import com.swd.bike.exception.InternalException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserTripController extends BaseController implements IUserTripController {
    @Override
    public ResponseEntity<ResponseBase<PageResponse<TripResponse>>> getAllTrips(GetAllTripsRequest request) {
        request.setUserId(getUserId());
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<TripDetailResponse>> getCurrentTrip() {
        GetOnGoingTripRequest request = new GetOnGoingTripRequest();
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<TripDetailResponse>> getTrip(Long id) {
        GetTripRequest request = new GetTripRequest();
        request.setId(id);
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<StatusResponse>> updateTrip(Long id, TripAction action) {
        switch (action) {
            case CANCEL:
                CancelTripRequest request = new CancelTripRequest();
                request.setId(id);
                return this.execute(request);
            case START:
                StartTripRequest startTripRequest = new StartTripRequest();
                startTripRequest.setId(id);
                return this.execute(startTripRequest);
            case FINISH:
                FinishTripRequest finishTripRequest = new FinishTripRequest();
                finishTripRequest.setId(id);
                return this.execute(finishTripRequest);
        }
        throw new InternalException(ResponseCode.INVALID_PARAM);
    }

    @Override
    public ResponseEntity<ResponseBase<StatusResponse>> feedbackTrip(Long id, FeedbackTripRequest request) {
        request.setId(id);
        return this.execute(request);
    }
}
