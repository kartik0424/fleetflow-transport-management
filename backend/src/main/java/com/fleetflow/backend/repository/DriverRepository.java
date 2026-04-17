package com.fleetflow.backend.repository;

import com.fleetflow.backend.entity.Driver;
import com.fleetflow.backend.entity.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {
    boolean existsByPhone(String phone);

    boolean existsByLisenceNumber(String licenseNumber);

    List<Driver> findByStatus(DriverStatus status);
}
