package com.swd.bike.dto.trip;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TripModel {
    private Long id;
    private Long endStationId;
    private String endStationName;
    private Long startStationId;
    private String startStationName;
    private String title;
    private String feedbackContent;
    private Float feedbackPoint;
}
