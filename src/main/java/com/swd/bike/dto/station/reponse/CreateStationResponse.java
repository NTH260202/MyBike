package com.swd.bike.dto.station.reponse;

import com.swd.bike.core.BaseResponseData;
import com.swd.bike.enums.StationStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class CreateStationResponse extends BaseResponseData {
    private Long id;
    private String name;
    private String address;
    private String description;
    private Float longitude;
    private Float latitude;
    private StationStatus status;
    private List<Long> nextStationIds;
}
