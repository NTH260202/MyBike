package com.swd.bike.handler.station;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.post.QueryPostModel;
import com.swd.bike.dto.station.reponse.UpdateStationStatusResponse;
import com.swd.bike.dto.station.request.UpdateStationStatusRequest;
import com.swd.bike.dto.trip.QueryTripModel;
import com.swd.bike.entity.Station;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.enums.StationStatus;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IPostService;
import com.swd.bike.service.interfaces.IStationService;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateStationStatusHandler extends RequestHandler<UpdateStationStatusRequest, UpdateStationStatusResponse> {
    private final ITripService tripService;
    private final IStationService IStationService;

    private final IPostService postService;
    @Override
    public UpdateStationStatusResponse handle(UpdateStationStatusRequest request) {
        Station station = IStationService.getStationById(request.getId());

        if (request.getStatus().equals(StationStatus.INACTIVE)) {

            boolean isUsed = tripService.checkExists(QueryTripModel.builder()
                    .startStationId(station.getId())
                    .endStationId(station.getId())
                    .build()
                    .getTripByNotDoneStatusAndStartStationIdOrEndStationId())
                    && postService.isExistWithActiveStation(QueryPostModel.builder()
                    .endStationId(station.getId())
                    .startStationId(station.getId())
                    .build()
                    .getPostByNotDoneStatusAndStartStationIdOrEndStationId());
            if (isUsed) {
                throw new InternalException(ResponseCode.STATION_IS_USED);
            }
        }

        station.setStatus(request.getStatus());
        IStationService.createOrUpdate(station);
        return UpdateStationStatusResponse.builder().success(true).build();
    }
}
