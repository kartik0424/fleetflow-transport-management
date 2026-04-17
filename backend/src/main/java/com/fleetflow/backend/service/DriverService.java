package com.fleetflow.backend.service;

import com.fleetflow.backend.entity.Driver;
import com.fleetflow.backend.entity.DriverStatus;
import com.fleetflow.backend.entity.Vehicle;
import com.fleetflow.backend.repository.DriverRepository;
import com.fleetflow.backend.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DriverService {
    private final DriverRepository repo;
    private final VehicleRepository vehicleRepo;

    public DriverService(DriverRepository repo, VehicleRepository vehicleRepo){
        this.repo = repo;
        this.vehicleRepo = vehicleRepo;
    }

    public List<Driver> getAllDrivers(){
        return repo.findAll();
    }

    public Driver saveDriver(Driver driver){
        if(repo.existsByPhone(driver.getPhone())){
            throw new RuntimeException("Phone already exists");
        }

        if(repo.existsByLisenceNumber(driver.getLicenseNumber())){
            throw new RuntimeException("License already exists");
        }

        if(driver.getLicenseExpiry().isBefore(LocalDate.now())){
            throw new RuntimeException("License already expired!");
        }

        return repo.save(driver);
    }

    public void deleteDriver(Long id){
        repo.deleteById(id);
    }

    public List<Driver> getDriverByStatus(DriverStatus status){
            return repo.findByStatus(status);
    }

    public List<Driver> getAvailableDrivers() {
        return repo.findByStatus(DriverStatus.AVAILABLE);
    }

    public Driver assignVehicle(Long driverId, Long vehicleId){
        Driver driver = repo.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        driver.setVehicle(vehicle);
        driver.setStatus(DriverStatus.ON_TRIP);

        return repo.save(driver);
    }
}
