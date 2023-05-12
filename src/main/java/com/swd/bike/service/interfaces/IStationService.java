package com.swd.bike.service.interfaces;

import com.swd.bike.entity.Station;
import com.swd.bike.enums.StationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IStationService {
    Station createOrUpdate(Station request);
    Station getRefById(Long id);
    Station getStationById(Long id);
    boolean checkStationActive(Long id);
    boolean checkStationsActive(List<Long> id);
    Page<Station> getStationPage(Specification<Station> specification, Pageable pageable);
    List<Station> findAllByIds(List<Long> ids);
    boolean isActiveStation(Station station);
    Station findStation(Long id);
    List<Station> getAllStations();
    List<Station> getAllStations(Long fromStationId);
    List<Station> getAllActiveStations();
    List<Station> getAllActiveStations(Long fromStationId);
}
