package com.fleetflow.backend.service;

import com.fleetflow.backend.entity.Driver;
import com.fleetflow.backend.entity.DriverStatus;
import com.fleetflow.backend.entity.Vehicle;
import com.fleetflow.backend.entity.VehicleStatus;
import com.fleetflow.backend.repository.DriverRepository;
import com.fleetflow.backend.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DriverService {

    private final DriverRepository repo;
    private final VehicleRepository vehicleRepo;

    public DriverService(DriverRepository repo, VehicleRepository vehicleRepo) {
        this.repo = repo;
        this.vehicleRepo = vehicleRepo;
    }

    // Get all drivers
    public List<Driver> getAllDrivers() {
        return repo.findAll();
    }

    // Save new driver
    public Driver saveDriver(Driver driver) {

        if (repo.existsByPhone(driver.getPhone())) {
            throw new RuntimeException("Phone already exists");
        }

        if (repo.existsByLicenseNumber(driver.getLicenseNumber())) {
            throw new RuntimeException("License already exists");
        }

        if (driver.getLicenseExpiry().isBefore(LocalDate.now())) {
            throw new RuntimeException("License already expired");
        }

        if (driver.getStatus() == null) {
            driver.setStatus(DriverStatus.AVAILABLE);
        }

        return repo.save(driver);
    }

    // Delete driver
    public void deleteDriver(Long id) {

        Driver driver = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        repo.delete(driver);
    }

    // Get by status
    public List<Driver> getDriverByStatus(DriverStatus status) {
        return repo.findByStatus(status);
    }

    // Available drivers
    public List<Driver> getAvailableDrivers() {
        return repo.findByStatus(DriverStatus.AVAILABLE);
    }

    // Assign vehicle
    public Driver assignVehicle(Long driverId, Long vehicleId) {

        Driver driver = repo.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        if (driver.getLicenseExpiry().isBefore(LocalDate.now())) {
            throw new RuntimeException("Driver license expired");
        }

        if (driver.getStatus() != DriverStatus.AVAILABLE) {
            throw new RuntimeException("Driver not available");
        }

        if (vehicle.getStatus() != VehicleStatus.AVAILABLE) {
            throw new RuntimeException("Vehicle not available");
        }

        driver.setVehicle(vehicle);
        driver.setStatus(DriverStatus.ON_TRIP);

        vehicle.setStatus(VehicleStatus.ON_TRIP);

        vehicleRepo.save(vehicle);

        return repo.save(driver);
    }

    // Complete trip / free resources
    public Driver completeTrip(Long driverId) {

        Driver driver = repo.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        Vehicle vehicle = driver.getVehicle();

        if (vehicle != null) {
            vehicle.setStatus(VehicleStatus.AVAILABLE);
            vehicleRepo.save(vehicle);
        }

        driver.setVehicle(null);
        driver.setStatus(DriverStatus.AVAILABLE);

        return repo.save(driver);
    }

    // Mark inactive
    public Driver deactivateDriver(Long driverId) {

        Driver driver = repo.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        driver.setStatus(DriverStatus.INACTIVE);

        return repo.save(driver);
    }
}