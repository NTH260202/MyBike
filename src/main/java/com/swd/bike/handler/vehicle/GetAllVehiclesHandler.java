package com.swd.bike.handler.vehicle;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.vehicle.request.GetAllVehiclesRequest;
import com.swd.bike.dto.vehicle.response.VehicleResponse;
import com.swd.bike.entity.Vehicle;
import com.swd.bike.service.interfaces.IVehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetAllVehiclesHandler extends RequestHandler<GetAllVehiclesRequest, PageResponse<VehicleResponse>> {

    private final IVehicleService vehicleService;

    @Override
    public PageResponse<VehicleResponse> handle(GetAllVehiclesRequest request) {
        Page<Vehicle> page = vehicleService.getAll(request.getSpecification(), request.getPageable());
        PageResponse<VehicleResponse> response = new PageResponse<>(page);

        response.setItems(page.getContent()
                .stream()
                .map(VehicleResponse::convert)
                .collect(Collectors.toList()));
        return response;
    }
}
