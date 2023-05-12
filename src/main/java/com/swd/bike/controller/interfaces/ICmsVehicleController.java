package com.swd.bike.controller.interfaces;

import com.swd.bike.config.OpenAPIConfig;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.vehicle.request.GetAllVehiclesRequest;
import com.swd.bike.dto.vehicle.request.UpdateVehicleStatusRequest;
import com.swd.bike.dto.vehicle.response.VehicleDetailResponse;
import com.swd.bike.dto.vehicle.response.VehicleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "/api/cms/v1/vehicles")
@Tag(name = "[Cms] Vehicles Controller", description = "Thao tác với phương tiện đăng ký")
@SecurityRequirement(name = OpenAPIConfig.BEARER_SCHEME)
public interface ICmsVehicleController {
    @Operation(summary = "Get vehicle by id")
    @GetMapping("/{id}")
    ResponseEntity<ResponseBase<VehicleDetailResponse>> get(@PathVariable Long id);

    @Operation(summary = "Get all vehicles")
    @GetMapping()
    ResponseEntity<ResponseBase<PageResponse<VehicleResponse>>> getAll(@ParameterObject GetAllVehiclesRequest request);

    @Operation(summary = "Approve/Disapprove vehicle")
    @PutMapping("/{id}/status")
    ResponseEntity<ResponseBase<StatusResponse>> updateStatus(@PathVariable Long id,
                                                              @Valid @RequestBody UpdateVehicleStatusRequest request);
}
