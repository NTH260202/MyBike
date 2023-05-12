package com.swd.bike.dto.station.reponse;

import com.swd.bike.core.BaseResponseData;
import com.swd.bike.enums.StationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class StationPageResponse extends BaseResponseData {
    private Long id;
    private String name;
    private String address;
    private String description;
    private StationStatus status;
    private float longitude;
    private float latitude;
}
