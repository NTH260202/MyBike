package com.swd.bike.repository;

import com.swd.bike.entity.Vehicle;
import com.swd.bike.enums.VehicleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByOwnerId(String id);

    Page<Vehicle> findAll(Specification<Vehicle> specification, Pageable pageable);
    int countByStatusAndCreatedAtBetween(VehicleStatus status, LocalDateTime from, LocalDateTime to);

}