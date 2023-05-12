package com.swd.bike.dto.vehicle;

import com.swd.bike.entity.Account;
import com.swd.bike.enums.VehicleStatus;
import com.swd.bike.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class VehicleModel {
    private Long id;
    private String brand;
    private String licencePlate; // vehicle number
    private String color;
    private String image;
    private String description;
    private VehicleType type;
    private VehicleStatus status;
}
