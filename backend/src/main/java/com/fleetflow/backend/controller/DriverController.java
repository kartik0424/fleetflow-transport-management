package com.fleetflow.backend.controller;

import com.fleetflow.backend.entity.Driver;
import com.fleetflow.backend.entity.DriverStatus;
import com.fleetflow.backend.service.DriverService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@CrossOrigin("*")
public class DriverController {

    private final DriverService drive;

    public DriverController(DriverService drive) {
        this.drive = drive;
    }

    // Add new driver
    @PostMapping
    public Driver addDriver(@RequestBody Driver driver) {
        return drive.saveDriver(driver);
    }

    // Get all drivers
    @GetMapping
    public List<Driver> getAllDrivers() {
        return drive.getAllDrivers();
    }

    // Get driver by id
    @GetMapping("/{id}")
    public Driver getDriverById(@PathVariable Long id) {
        return drive.getAllDrivers()
                .stream()
                .filter(driver -> driver.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Driver not found"));
    }

    // Get available drivers
    @GetMapping("/available")
    public List<Driver> getAvailableDrivers() {
        return drive.getAvailableDrivers();
    }

    // Get drivers by status
    @GetMapping("/status")
    public List<Driver> getDriverByStatus(@RequestParam DriverStatus status) {
        return drive.getDriverByStatus(status);
    }

    // Assign vehicle to driver
    @PutMapping("/{driverId}/assign/{vehicleId}")
    public Driver assignVehicle(@PathVariable Long driverId,
                                @PathVariable Long vehicleId) {
        return drive.assignVehicle(driverId, vehicleId);
    }

    // Complete trip
    @PutMapping("/{driverId}/complete-trip")
    public Driver completeTrip(@PathVariable Long driverId) {
        return drive.completeTrip(driverId);
    }

    // Deactivate driver
    @PutMapping("/{driverId}/deactivate")
    public Driver deactivateDriver(@PathVariable Long driverId) {
        return drive.deactivateDriver(driverId);
    }

    // Delete driver
    @DeleteMapping("/{id}")
    public String deleteDriver(@PathVariable Long id) {
        drive.deleteDriver(id);
        return "Driver Deleted Successfully";
    }
}