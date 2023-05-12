package com.swd.bike.repository;

import com.swd.bike.entity.Station;
import com.swd.bike.enums.StationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Long>, JpaSpecificationExecutor<Station> {
    @Override
    @Query("Select s from Station s left join fetch s.nextStation where s.id = :id")
    Optional<Station> findById(Long id);
    Page<Station> findAll(Specification<Station> specification, Pageable pageable);
    Optional<Station> findStationByIdAndStatus(Long id, StationStatus status);

    @Query("SELECT endStations " +
            "FROM Station station " +
            "JOIN station.nextStation endStations " +
            "WHERE station.id = ?1")
    List<Station> findByFromStationId(Long fromStationId);

    List<Station> findAllByStatus(StationStatus status);

    @Query("SELECT endStation " +
            "FROM Station station " +
            "JOIN station.nextStation endStation " +
            "WHERE station.id = ?1 AND endStation.status = ?2")
    List<Station> findAllByFromStationIdAndStatus(Long fromStationId, StationStatus status);
}
