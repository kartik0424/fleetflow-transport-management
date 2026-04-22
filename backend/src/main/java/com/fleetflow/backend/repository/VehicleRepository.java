package com.fleetflow.backend.repository;

import com.fleetflow.backend.entity.Vehicle;
import com.fleetflow.backend.entity.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByStatus(VehicleStatus status);

    boolean existsByVehicleNumber(String vehicleNumber);
}
