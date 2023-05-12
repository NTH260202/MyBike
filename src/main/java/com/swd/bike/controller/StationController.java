package com.swd.bike.controller;

import com.swd.bike.controller.interfaces.ICmsStationController;
import com.swd.bike.core.BaseController;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.station.reponse.*;
import com.swd.bike.dto.station.request.*;
import com.swd.bike.enums.StationStatus;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class StationController extends BaseController implements ICmsStationController {
    @Override
    public ResponseEntity<ResponseBase<CreateStationResponse>> create(
            @RequestBody @Valid CreateStationRequest request) {
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<UpdateStationResponse>> update(
            @PathVariable long id,
            @RequestBody @Valid UpdateStationRequest request) {
        request.setId(id);
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<UpdateStationStatusResponse>> update(
            @PathVariable long id,
            @RequestParam(required = true) StationStatus status) {
        UpdateStationStatusRequest request = new UpdateStationStatusRequest();
        request.setId(id);
        request.setStatus(status);
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<GetDetailResponse>> getDetailById(
            @PathVariable long id) {
        GetDetailRequest request = new GetDetailRequest();
        request.setId(id);
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<PageResponse<StationPageResponse>>> getPageByFilter(
            @ParameterObject GetPageByFilterRequest request) {
        return this.execute(request);
    }
}
