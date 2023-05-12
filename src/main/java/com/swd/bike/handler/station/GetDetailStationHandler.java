package com.swd.bike.handler.station;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.station.StationModel;
import com.swd.bike.dto.station.reponse.GetDetailResponse;
import com.swd.bike.dto.station.request.GetDetailRequest;
import com.swd.bike.entity.Station;
import com.swd.bike.service.interfaces.IStationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetDetailStationHandler extends RequestHandler<GetDetailRequest, GetDetailResponse> {
    private final IStationService IStationService;
    @Override
    public GetDetailResponse handle(GetDetailRequest request) {
        Station station = IStationService.getStationById(request.getId());
        return GetDetailResponse.builder()
                .id(station.getId())
                .name(station.getName())
                .address(station.getAddress())
                .longitude(station.getLongitude())
                .latitude(station.getLatitude())
                .description(station.getDescription())
                .status(station.getStatus())
                .nextStations(station.getNextStation() == null
                        ? new ArrayList<>()
                        : station.getNextStation()
                        .stream()
                        .map(stationEntity -> StationModel.builder()
                                .id(stationEntity.getId())
                                .name(stationEntity.getName())
                                .longitude(stationEntity.getLongitude())
                                .latitude(stationEntity.getLatitude())
                                .description(stationEntity.getDescription())
                                .address(stationEntity.getAddress())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
