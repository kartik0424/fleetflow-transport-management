package com.fleetflow.backend.controller;

import com.fleetflow.backend.entity.Vehicle;
import com.fleetflow.backend.entity.VehicleStatus;
import com.fleetflow.backend.service.VehicleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin("*")
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    // Get all vehicles
    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return service.getAllVehicles();
    }

    // Get vehicle by id
    @GetMapping("/{id}")
    public Vehicle getVehicleById(@PathVariable Long id) {
        return service.getVehicleById(id);
    }

    // Add new vehicle
    @PostMapping
    public Vehicle addVehicle(@RequestBody Vehicle vehicle) {
        return service.saveVehicle(vehicle);
    }

    // Update vehicle
    @PutMapping("/{id}")
    public Vehicle updateVehicle(@PathVariable Long id,
                                 @RequestBody Vehicle vehicle) {
        return service.updateVehicle(id, vehicle);
    }

    // Get by status
    @GetMapping("/status")
    public List<Vehicle> getByStatus(@RequestParam VehicleStatus status) {
        return service.getVehiclesByStatus(status);
    }

    // Get available vehicles
    @GetMapping("/available")
    public List<Vehicle> getAvailableVehicles() {
        return service.getAvailableVehicles();
    }

    // Mark maintenance
    @PutMapping("/{id}/maintenance")
    public Vehicle markMaintenance(@PathVariable Long id) {
        return service.markMaintenance(id);
    }

    // Mark available
    @PutMapping("/{id}/available")
    public Vehicle makeAvailable(@PathVariable Long id) {
        return service.makeAvailable(id);
    }

    // Delete vehicle
    @DeleteMapping("/{id}")
    public String deleteVehicle(@PathVariable Long id) {
        service.deleteVehicle(id);
        return "Vehicle Deleted Successfully";
    }
}