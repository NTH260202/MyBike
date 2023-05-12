package com.swd.bike.dto.vehicle.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swd.bike.core.BaseRequestData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UpdateVehicleStatusRequest extends BaseRequestData {
    @JsonIgnore
    private Long id;
    private boolean approved;

}
