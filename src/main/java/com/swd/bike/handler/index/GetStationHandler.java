package com.swd.bike.handler.index;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.ListResponse;
import com.swd.bike.dto.index.GetStationsRequest;
import com.swd.bike.dto.userPost.response.StationResponse;
import com.swd.bike.entity.Station;
import com.swd.bike.service.interfaces.IStationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetStationHandler extends RequestHandler<GetStationsRequest, ListResponse<StationResponse>> {

    private final IStationService stationService;

    @Override
    @Transactional
    public ListResponse<StationResponse> handle(GetStationsRequest request) {
        Long fromStationId = request.getFromStationId();
        List<Station> stations = Objects.nonNull(fromStationId)
                ? stationService.getAllActiveStations(fromStationId)
                : stationService.getAllActiveStations();

        String query = normalize(request.getQuery());
        if (StringUtils.isNotBlank(query)) {
            stations = stations.stream().filter(
                    station -> StringUtils.containsIgnoreCase(station.getName(), query)
                            || StringUtils.containsIgnoreCase(station.getAddress(), query)
                            || StringUtils.containsIgnoreCase(station.getDescription(), query)
            ).collect(Collectors.toList());
        }


        ListResponse<StationResponse> response = new ListResponse<>();
        response.setItems(stations.stream()
                .map(StationResponse::new)
                .collect(Collectors.toList()));

        return response;
    }

    private String normalize(String str) {
        if (str == null) {
            return null;
        }
        return str.trim().toLowerCase();
    }

}
