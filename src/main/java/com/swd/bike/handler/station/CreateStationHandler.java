package com.swd.bike.handler.station;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.station.reponse.CreateStationResponse;
import com.swd.bike.dto.station.request.CreateStationRequest;
import com.swd.bike.entity.Station;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.enums.StationStatus;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CreateStationHandler extends RequestHandler<CreateStationRequest, CreateStationResponse> {

    private final IStationService stationService;
    @Override
    public CreateStationResponse handle(CreateStationRequest request) {

        List<Station> nextStations = request.getNextStationIds().stream()
                .map(stationService::getRefById)
                .collect(Collectors.toList());
        if (!request.getNextStationIds().isEmpty() && !stationService.checkStationsActive(request.getNextStationIds())) {
            throw new InternalException(ResponseCode.STATION_IS_INACTIVE);
        }

        Station station = Station.builder()
                .address(request.getAddress())
                .name(request.getName())
                .longitude(request.getLongitude())
                .latitude(request.getLatitude())
                .description(request.getDescription())
                .status(StationStatus.ACTIVE)
                .nextStation(nextStations)
                .build();
        stationService.createOrUpdate(station);
        return CreateStationResponse.builder()
                .id(station.getId())
                .name(station.getName())
                .address(station.getAddress())
                .longitude(station.getLongitude())
                .latitude(station.getLatitude())
                .description(station.getDescription())
                .nextStationIds(station.getNextStation() == null
                        ? new ArrayList<>()
                        : station.getNextStation().stream()
                        .map(Station::getId)
                        .collect(Collectors.toList()))
                .status(station.getStatus())
                .build();
    }
}
