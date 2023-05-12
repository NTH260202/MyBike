package com.swd.bike.handler.vehicle;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.vehicle.request.GetUserVehicleRequest;
import com.swd.bike.dto.vehicle.response.VehicleResponse;
import com.swd.bike.entity.Account;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetUserVehicleHandler extends RequestHandler<GetUserVehicleRequest, VehicleResponse> {
    private final IAccountService accountService;

    @Override
    public VehicleResponse handle(GetUserVehicleRequest request) {
        Account account = accountService.getById(request.getUserId());
        if (account == null) {
            throw new InternalException(ResponseCode.ACCOUNT_NOT_FOUND);
        }
        if (account.getVehicle() == null) {
            throw new InternalException(ResponseCode.VEHICLE_NOT_FOUND);
        }
        return VehicleResponse.convert(account.getVehicle());
    }
}
