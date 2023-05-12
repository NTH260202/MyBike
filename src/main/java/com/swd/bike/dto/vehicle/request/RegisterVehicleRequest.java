package com.swd.bike.dto.vehicle.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.swd.bike.common.TrimString;
import com.swd.bike.core.BaseRequestData;
import com.swd.bike.enums.VehicleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class RegisterVehicleRequest extends BaseRequestData {
    @NotBlank
    @JsonDeserialize(using = TrimString.class)
    private String brand;
    @NotBlank
    @JsonDeserialize(using = TrimString.class)
    private String licencePlate;
    @NotBlank
    @JsonDeserialize(using = TrimString.class)
    private String color;
    @NotBlank
    @JsonDeserialize(using = TrimString.class)
    private String image;
    @JsonDeserialize(using = TrimString.class)
    private String description;
    @NotNull
    @Enumerated(EnumType.STRING)
    private VehicleType type;
}
