package com.swd.bike.service;

import com.swd.bike.entity.Vehicle;
import com.swd.bike.repository.VehicleRepository;
import com.swd.bike.service.interfaces.IVehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleService implements IVehicleService {
    private final VehicleRepository vehicleRepository;

    @CacheEvict(value = "account", key = "#vehicle.owner.id", condition = "#vehicle != null && #vehicle.owner.id != null && #vehicle.owner != null")
    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle getById(Long id) {
        if (id == null) {
            return null;
        }
        return vehicleRepository.findById(id).orElse(null);
    }

    public Vehicle getByOwnerId(String id) {
        if (id == null) {
            return null;
        }
        return vehicleRepository.findByOwnerId(id).orElse(null);
    }

    @Override
    public void delete(Vehicle vehicle) {
        if (vehicle == null) {
            return;
        }
        vehicleRepository.delete(vehicle);
    }

    @Override
    public Page<Vehicle> getAll(Specification<Vehicle> specification, Pageable pageable) {
        return vehicleRepository.findAll(specification, pageable);
    }
}
