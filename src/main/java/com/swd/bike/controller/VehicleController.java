package com.swd.bike.controller;

import com.swd.bike.controller.interfaces.ICmsVehicleController;
import com.swd.bike.core.BaseController;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.vehicle.request.GetAllVehiclesRequest;
import com.swd.bike.dto.vehicle.request.GetVehicleRequest;
import com.swd.bike.dto.vehicle.request.UpdateVehicleStatusRequest;
import com.swd.bike.dto.vehicle.response.VehicleDetailResponse;
import com.swd.bike.dto.vehicle.response.VehicleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehicleController extends BaseController implements ICmsVehicleController {


    @Override
    public ResponseEntity<ResponseBase<VehicleDetailResponse>> get(Long id) {
        return this.execute(new GetVehicleRequest(id));
    }

    @Override
    public ResponseEntity<ResponseBase<PageResponse<VehicleResponse>>> getAll(GetAllVehiclesRequest request) {
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<StatusResponse>> updateStatus(Long id, UpdateVehicleStatusRequest request) {
        return this.execute(request.setId(id));
    }
}
