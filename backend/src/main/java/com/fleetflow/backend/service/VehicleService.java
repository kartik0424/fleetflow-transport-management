package com.fleetflow.backend.service;

import com.fleetflow.backend.entity.Vehicle;
import com.fleetflow.backend.entity.VehicleStatus;
import com.fleetflow.backend.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository repo;

    public VehicleService(VehicleRepository repo) {
        this.repo = repo;
    }

    // Get all vehicles
    public List<Vehicle> getAllVehicles() {
        return repo.findAll();
    }

    // Get vehicle by id
    public Vehicle getVehicleById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }

    // Get vehicles by status
    public List<Vehicle> getVehiclesByStatus(VehicleStatus status) {
        return repo.findByStatus(status);
    }

    // Get only available vehicles
    public List<Vehicle> getAvailableVehicles() {
        return repo.findByStatus(VehicleStatus.AVAILABLE);
    }

    // Add new vehicle
    public Vehicle saveVehicle(Vehicle vehicle) {

        if (repo.existsByVehicleNumber(vehicle.getVehicleNumber())) {
            throw new RuntimeException("Vehicle number already exists");
        }

        if (vehicle.getCapacity() <= 0) {
            throw new RuntimeException("Invalid vehicle capacity");
        }

        if (vehicle.getInsuranceExpiry().isBefore(LocalDate.now())) {
            throw new RuntimeException("Insurance already expired");
        }

        if (vehicle.getStatus() == null) {
            vehicle.setStatus(VehicleStatus.AVAILABLE);
        }

        return repo.save(vehicle);
    }

    // Update vehicle
    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) {

        Vehicle vehicle = getVehicleById(id);

        vehicle.setBrandName(updatedVehicle.getBrandName());
        vehicle.setVehicleType(updatedVehicle.getVehicleType());
        vehicle.setCapacity(updatedVehicle.getCapacity());
        vehicle.setFuelType(updatedVehicle.getFuelType());
        vehicle.setInsuranceExpiry(updatedVehicle.getInsuranceExpiry());
        vehicle.setLastServiceDate(updatedVehicle.getLastServiceDate());

        return repo.save(vehicle);
    }

    // Mark under maintenance
    public Vehicle markMaintenance(Long id) {

        Vehicle vehicle = getVehicleById(id);

        if (vehicle.getStatus() == VehicleStatus.ON_TRIP) {
            throw new RuntimeException("Vehicle currently on trip");
        }

        vehicle.setStatus(VehicleStatus.MAINTENANCE);

        return repo.save(vehicle);
    }

    // Make vehicle available again
    public Vehicle makeAvailable(Long id) {

        Vehicle vehicle = getVehicleById(id);

        vehicle.setStatus(VehicleStatus.AVAILABLE);

        return repo.save(vehicle);
    }

    // Delete vehicle
    public void deleteVehicle(Long id) {

        Vehicle vehicle = getVehicleById(id);

        repo.delete(vehicle);
    }
}