package com.swd.bike.handler.vehicle;

import com.swd.bike.common.NotificationConstant;
import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.notification.dtos.NotificationDto;
import com.swd.bike.dto.vehicle.request.UpdateVehicleRequest;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Vehicle;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.enums.VehicleStatus;
import com.swd.bike.enums.notification.NotificationAction;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IAccountService;
import com.swd.bike.service.interfaces.IPushNotificationService;
import com.swd.bike.service.interfaces.IVehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateVehicleHandler extends RequestHandler<UpdateVehicleRequest, StatusResponse> {

    private final IVehicleService vehicleService;
    private final IAccountService accountService;
    private final IPushNotificationService pushNotificationService;

    @Override
    @Transactional
    public StatusResponse handle(UpdateVehicleRequest request) {
        Account account = accountService.getById(request.getUserId());
        if (account == null) {
            throw new InternalException(ResponseCode.ACCOUNT_NOT_FOUND);
        }
        if (account.getVehicle() == null) {
            throw new InternalException(ResponseCode.VEHICLE_NOT_FOUND);
        }
        boolean isUpdate = false;
        Vehicle vehicle = account.getVehicle();
        if (Strings.isNotBlank(request.getBrand())) {
            vehicle.setBrand(request.getBrand());
            isUpdate = true;
        }
        if (Strings.isNotBlank(request.getLicencePlate())) {
            vehicle.setLicencePlate(request.getLicencePlate());
            isUpdate = true;
        }
        if (Strings.isNotBlank(request.getColor())) {
            vehicle.setColor(request.getColor());
            isUpdate = true;
        }
        if (Strings.isNotBlank(request.getImage())) {
            vehicle.setImage(request.getImage());
            isUpdate = true;
        }
        if (Strings.isNotBlank(request.getDescription())) {
            vehicle.setDescription(request.getDescription());
            isUpdate = true;
        }
        if (Objects.nonNull(request.getType())) {
            vehicle.setType(request.getType());
            isUpdate = true;
        }
        if (isUpdate) {
            vehicle.setStatus(VehicleStatus.WAITING);
        }

        // Notify to admin
        pushNotificationService.sendAdmin(new NotificationDto()
                .setTitle(NotificationConstant.Title.VEHICLE_UPDATE)
                .setBody(String.format(NotificationConstant.Body.VEHICLE_UPDATE, account.getName(), account.getId()))
                .setAction(NotificationAction.OPEN_VEHICLE)
                .setReferenceId(String.valueOf(vehicle.getId())));

        return new StatusResponse(vehicleService.save(vehicle) != null);
    }
}
