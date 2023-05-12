package com.swd.bike.dto.station;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class StationModel {
    private Long id;
    private String name;
    private String address;
    private String description;
    private Float longitude;
    private Float latitude;
}
