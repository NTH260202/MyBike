package com.swd.bike.dto.station.reponse;

import com.swd.bike.core.BaseResponseData;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class UpdateStationResponse extends BaseResponseData {
    private Long id;
    private String name;
    private String address;
    private String description;
    private Float longitude;
    private Float latitude;
    private List<Long> nextStationIds;
}
