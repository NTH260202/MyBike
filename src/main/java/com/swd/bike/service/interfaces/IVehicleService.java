package com.swd.bike.service.interfaces;

import com.swd.bike.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface IVehicleService {
    Vehicle save(Vehicle vehicle);

    Vehicle getById(Long id);

    Vehicle getByOwnerId(String id);
    void delete(Vehicle vehicle);

    Page<Vehicle> getAll(Specification<Vehicle> specification, Pageable pageable);
}
