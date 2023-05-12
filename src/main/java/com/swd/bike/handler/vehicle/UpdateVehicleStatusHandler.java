package com.swd.bike.handler.vehicle;

import com.swd.bike.common.NotificationConstant;
import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.notification.dtos.NotificationDto;
import com.swd.bike.dto.vehicle.request.UpdateVehicleStatusRequest;
import com.swd.bike.entity.Vehicle;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.enums.VehicleStatus;
import com.swd.bike.enums.notification.NotificationAction;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IPushNotificationService;
import com.swd.bike.service.interfaces.IVehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateVehicleStatusHandler extends RequestHandler<UpdateVehicleStatusRequest, StatusResponse> {

    private final IVehicleService vehicleService;
    private final IPushNotificationService pushNotificationService;

    @Override
    @Transactional
    public StatusResponse handle(UpdateVehicleStatusRequest request) {
        Vehicle vehicle = vehicleService.getById(request.getId());
        if (vehicle == null || vehicle.getOwner() == null) {
            throw new InternalException(ResponseCode.VEHICLE_NOT_FOUND);
        }
        vehicle.setStatus(request.isApproved() ? VehicleStatus.APPROVED : VehicleStatus.DENY);

        // Notify to admin
        if (request.isApproved()) {
            pushNotificationService.sendTo(vehicle.getOwner().getId(), new NotificationDto()
                    .setTitle(NotificationConstant.Title.VEHICLE_REGISTRATION_APPROVE)
                    .setBody(String.format(NotificationConstant.Body.VEHICLE_REGISTRATION_APPROVE, vehicle.getBrand(), vehicle.getId()))
                    .setAction(NotificationAction.OPEN_VEHICLE)
                    .setReferenceId(String.valueOf(vehicle.getId())));
        } else {
            pushNotificationService.sendTo(vehicle.getOwner().getId(), new NotificationDto()
                    .setTitle(NotificationConstant.Title.VEHICLE_REGISTRATION_DENIED)
                    .setBody(String.format(NotificationConstant.Body.VEHICLE_REGISTRATION_DENIED, vehicle.getBrand(), vehicle.getId()))
                    .setAction(NotificationAction.OPEN_VEHICLE)
                    .setReferenceId(String.valueOf(vehicle.getId())));
        }


        return new StatusResponse(vehicleService.save(vehicle) != null);
    }
}
