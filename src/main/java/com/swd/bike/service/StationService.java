package com.swd.bike.service;

import com.swd.bike.entity.Station;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.enums.StationStatus;
import com.swd.bike.exception.InternalException;
import com.swd.bike.repository.StationRepository;
import com.swd.bike.service.interfaces.IStationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class StationService implements IStationService {
    private final StationRepository stationRepository;

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = {"station"}, key = "#station.id", condition = "#station.id != null"),
            @CacheEvict(cacheNames = "pageStation", allEntries = true),
            @CacheEvict(cacheNames = "listStation", allEntries = true),
            @CacheEvict(cacheNames = "activeStationList", allEntries = true)
    })
    public Station createOrUpdate(Station station) {
        return stationRepository.save(station);
    }

    @Override
    public Station getRefById(Long id) {
        return stationRepository.getReferenceById(id);
    }

    @Override
    @Cacheable(cacheNames = "station", key = "#id")
    public Station getStationById(Long id) {
        log.info("Detail Station Is Not Cached");
        return stationRepository.findById(id).orElseThrow(() -> new InternalException(ResponseCode.STATION_NOT_FOUND));
    }

    @Override
    public boolean checkStationActive(Long id) {
        Station station = stationRepository.findStationByIdAndStatus(id, StationStatus.ACTIVE).orElse(null);
        return station != null;
    }

    @Override
    public boolean checkStationsActive(List<Long> ids) {
        List<Station> stations = stationRepository.findAllById(ids);
        if (stations.isEmpty()) {
            throw new InternalException(ResponseCode.STATION_NOT_FOUND);
        }
        return stations.stream().allMatch(station -> station.getStatus().equals(StationStatus.ACTIVE));
    }

    @Override
    public Page<Station> getStationPage(Specification<Station> specification, Pageable pageable) {
        return stationRepository.findAll(specification, pageable);
    }

    @Override
    @Cacheable(cacheNames = "listStation", key = "#ids")
    public List<Station> findAllByIds(List<Long> ids) {
        return stationRepository.findAllById(ids);
    }

    @Override
    public boolean isActiveStation(Station station) {
        return station != null && StationStatus.ACTIVE.equals(station.getStatus());
    }

    @Override
    public Station findStation(Long id) {
        if (id == null) {
            return null;
        }
        return stationRepository.findById(id)
                .orElse(null);
    }

    @Override
    @Cacheable(cacheNames = "listStation")
    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    @Override
    public List<Station> getAllStations(Long fromStationId) {
        if (fromStationId == null) {
            return getAllStations();
        }
        return stationRepository.findByFromStationId(fromStationId);
    }

    @Override
    @Cacheable(cacheNames = "activeStationList")
    public List<Station> getAllActiveStations() {
        return stationRepository.findAllByStatus(StationStatus.ACTIVE);
    }

    @Override
    @Cacheable(cacheNames = "activeStationList", key = "#fromStationId")
    public List<Station> getAllActiveStations(Long fromStationId) {
        if (fromStationId == null) {
            return getAllActiveStations();
        }
        return stationRepository.findAllByFromStationIdAndStatus(fromStationId, StationStatus.ACTIVE);
    }
}
