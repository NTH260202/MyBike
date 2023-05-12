package com.swd.bike.dto.vehicle.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.swd.bike.common.TrimString;
import com.swd.bike.core.BaseRequestData;
import com.swd.bike.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UpdateVehicleRequest extends BaseRequestData {
    @JsonDeserialize(using = TrimString.class)
    private String brand;
    @JsonDeserialize(using = TrimString.class)
    private String licencePlate;
    @JsonDeserialize(using = TrimString.class)
    private String color;
    @JsonDeserialize(using = TrimString.class)
    private String image;
    @JsonDeserialize(using = TrimString.class)
    private String description;
    @NotNull
    @Enumerated(EnumType.STRING)
    private VehicleType type;
}
