package com.swd.bike.dto.vehicle.response;

import com.swd.bike.core.BaseResponseData;
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
public class VehicleResponse extends BaseResponseData {
    private Long id;
    private String brand;
    private String licencePlate;
    private String color;
    private String image;
    private String description;
    private String ownerId;
    private String ownerName;
    private VehicleType type;
    private VehicleStatus status;


    public static VehicleResponse convert(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }

        VehicleResponse response = new VehicleResponse()
                .setId(vehicle.getId())
                .setBrand(vehicle.getBrand())
                .setColor(vehicle.getColor())
                .setLicencePlate(vehicle.getLicencePlate())
                .setImage(vehicle.getImage())
                .setDescription(vehicle.getDescription())
                .setType(vehicle.getType())
                .setStatus(vehicle.getStatus());
        
        if (vehicle.getOwner() != null) {
            response.setOwnerId(vehicle.getOwner().getId())
                    .setOwnerName(vehicle.getOwner().getName());
        }

        return response;
    }
}
