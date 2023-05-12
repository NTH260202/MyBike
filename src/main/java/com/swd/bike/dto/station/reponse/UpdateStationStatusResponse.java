package com.swd.bike.dto.station.reponse;

import com.swd.bike.core.BaseResponseData;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Getter
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class UpdateStationStatusResponse extends BaseResponseData {
    private boolean success;
}
