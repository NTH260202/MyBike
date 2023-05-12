package com.swd.bike.handler.vehicle;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.vehicle.request.DeleteVehicleRequest;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Vehicle;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IAccountService;
import com.swd.bike.service.interfaces.IVehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteVehicleHandler extends RequestHandler<DeleteVehicleRequest, StatusResponse> {

    private final IVehicleService vehicleService;
    private final IAccountService accountService;

    @Override
    public StatusResponse handle(DeleteVehicleRequest request) {
        Account account = accountService.getById(request.getUserId());
        if (account == null) {
            throw new InternalException(ResponseCode.ACCOUNT_NOT_FOUND);
        }
        if (account.getVehicle() == null) {
            throw new InternalException(ResponseCode.VEHICLE_NOT_FOUND);
        }
        Vehicle vehicle = account.getVehicle();

        vehicleService.delete(vehicle);
        return new StatusResponse(true);
    }
}
