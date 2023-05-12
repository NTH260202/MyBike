package com.swd.bike.handler.station;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.station.reponse.StationPageResponse;
import com.swd.bike.dto.station.request.GetPageByFilterRequest;
import com.swd.bike.entity.Station;
import com.swd.bike.service.interfaces.IStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetPageByFilterHandler extends RequestHandler<GetPageByFilterRequest, PageResponse<StationPageResponse>> {
    private final IStationService IStationService;
    @Override
    @Cacheable(cacheNames = "pageStation", key = "#request.toString()")
    public PageResponse<StationPageResponse> handle(GetPageByFilterRequest request) {
        Page<Station> stations = IStationService.getStationPage(request.getSpecification(),request.getPageable());

        PageResponse<StationPageResponse> stationPageResponse = new PageResponse<>(stations);

        stationPageResponse.setItems(stations.getContent().stream()
                .map(station -> this.convertEntityToDTO(station))
                .collect(Collectors.toList()));

        return stationPageResponse;
    }

    private StationPageResponse convertEntityToDTO(Station station) {
        return StationPageResponse.builder()
                .id(station.getId())
                .address(station.getAddress())
                .description(station.getDescription())
                .name(station.getName())
                .status(station.getStatus())
                .build();

    }
}
