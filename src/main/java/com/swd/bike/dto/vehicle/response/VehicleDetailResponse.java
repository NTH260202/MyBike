package com.swd.bike.dto.vehicle.response;

import com.swd.bike.core.BaseResponseData;
import com.swd.bike.dto.account.response.AccountResponse;
import com.swd.bike.entity.Vehicle;
import com.swd.bike.enums.VehicleStatus;
import com.swd.bike.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class VehicleDetailResponse extends BaseResponseData {
    private Long id;
    private String brand;
    private String licencePlate;
    private String color;
    private String image;
    private String description;
    private VehicleType type;
    private VehicleStatus status;
    private AccountResponse owner;

    public static VehicleDetailResponse convert(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }

        return new VehicleDetailResponse()
                .setId(vehicle.getId())
                .setBrand(vehicle.getBrand())
                .setColor(vehicle.getColor())
                .setImage(vehicle.getImage())
                .setLicencePlate(vehicle.getLicencePlate())
                .setDescription(vehicle.getDescription())
                .setType(vehicle.getType())
                .setStatus(vehicle.getStatus())
                .setOwner(vehicle.getOwner() == null ? null : new AccountResponse(vehicle.getOwner()));
    }
}
