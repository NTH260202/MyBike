package com.swd.bike.handler.vehicle;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.vehicle.request.GetVehicleRequest;
import com.swd.bike.dto.vehicle.response.VehicleDetailResponse;
import com.swd.bike.entity.Vehicle;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IVehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetVehicleHandler extends RequestHandler<GetVehicleRequest, VehicleDetailResponse> {

    private final IVehicleService vehicleService;

    @Override
    public VehicleDetailResponse handle(GetVehicleRequest request) {
        Vehicle vehicle = vehicleService.getById(request.getId());
        if (vehicle == null) {
            throw new InternalException(ResponseCode.VEHICLE_NOT_FOUND);
        }

        return VehicleDetailResponse.convert(vehicle);
    }
}
