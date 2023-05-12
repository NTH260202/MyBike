package com.swd.bike.handler.station;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.post.QueryPostModel;
import com.swd.bike.dto.station.reponse.CreateStationResponse;
import com.swd.bike.dto.station.reponse.UpdateStationResponse;
import com.swd.bike.dto.station.request.CreateStationRequest;
import com.swd.bike.dto.station.request.UpdateStationRequest;
import com.swd.bike.dto.trip.QueryTripModel;
import com.swd.bike.entity.Station;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.enums.StationStatus;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IPostService;
import com.swd.bike.service.interfaces.IStationService;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateStationHandler extends RequestHandler<UpdateStationRequest, UpdateStationResponse> {
    private final IStationService stationService;
    private final ITripService tripService;
    private final IPostService postService;
    private final CreateStationHandler createStationHandler;

    @Override
    public UpdateStationResponse handle(UpdateStationRequest request) {
        boolean isUsed = tripService.checkExists(QueryTripModel.builder()
                .startStationId(request.getId())
                .endStationId(request.getId())
                .build()
                .getTripByNotDoneStatusAndStartStationIdOrEndStationId())
                && postService.isExistWithActiveStation(QueryPostModel.builder()
                .endStationId(request.getId())
                .startStationId(request.getId())
                .build()
                .getPostByNotDoneStatusAndStartStationIdOrEndStationId());

        if (isUsed) {
            throw new InternalException(ResponseCode.STATION_IS_USED);
        }
        Station updatedStation = stationService.getStationById(request.getId());
        if (updatedStation.getStatus().equals(StationStatus.INACTIVE) ) {
            throw new InternalException(ResponseCode.STATION_IS_INACTIVE);
        }

        List<Station> nextStations = stationService.findAllByIds(request.getNextStationIds());
        nextStations = nextStations.stream().filter(station -> station.getStatus().equals(StationStatus.ACTIVE)).collect(Collectors.toList());

        updatedStation.setAddress(request.getAddress());
        updatedStation.setName(request.getName());
        updatedStation.setLongitude(request.getLongitude());
        updatedStation.setLatitude(request.getLatitude());
        updatedStation.setDescription(request.getDescription());
        updatedStation.setNextStation(nextStations);

        stationService.createOrUpdate(updatedStation);

        UpdateStationResponse response = UpdateStationResponse.builder()
                .id(updatedStation.getId())
                .name(updatedStation.getName())
                .address(updatedStation.getAddress())
                .longitude(updatedStation.getLongitude())
                .latitude(updatedStation.getLatitude())
                .description(updatedStation.getDescription())
                .nextStationIds(request.getNextStationIds())
                .build();
        return response;
    }
}
