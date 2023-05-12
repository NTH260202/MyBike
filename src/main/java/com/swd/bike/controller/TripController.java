package com.swd.bike.controller;

import com.swd.bike.controller.interfaces.ICmsTripController;
import com.swd.bike.core.BaseController;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.trip.request.GetPageByFilterRequest;
import com.swd.bike.dto.trip.request.GetTripDetailRequest;
import com.swd.bike.dto.trip.response.GetTripDetailResponse;
import com.swd.bike.dto.trip.response.GetTripPageResponse;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TripController extends BaseController implements ICmsTripController {
    @Override
    public ResponseEntity<ResponseBase<GetTripDetailResponse>> getDetailById(
            @PathVariable long id) {
        GetTripDetailRequest request = new GetTripDetailRequest();
        request.setId(id);
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<PageResponse<GetTripPageResponse>>> getPageByFilter(
            @ParameterObject GetPageByFilterRequest request) {
        return this.execute(request);
    }
}
