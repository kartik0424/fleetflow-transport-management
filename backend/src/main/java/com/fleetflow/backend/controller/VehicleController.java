package com.fleetflow.backend.controller;

import com.fleetflow.backend.entity.Vehicle;
import com.fleetflow.backend.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin("*")
public class VehicleController {
    @Autowired
    private VehicleService service;

    @GetMapping
    public List<Vehicle> getAll() {
        return service.getAllVehicles();
    }

    @PostMapping
    public Vehicle add(@RequestBody Vehicle vehicle){
        return service.saveVehicle(vehicle);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        service.deleteVehicle(id);
        return "Deleted";
    }
}
