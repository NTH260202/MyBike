package com.swd.bike.dto.vehicle.request;

import com.swd.bike.core.BaseRequestData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetVehicleRequest extends BaseRequestData {
    private Long id;
}
