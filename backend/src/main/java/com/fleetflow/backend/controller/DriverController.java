package com.fleetflow.backend.controller;

import com.fleetflow.backend.entity.Driver;
import com.fleetflow.backend.entity.DriverStatus;
import com.fleetflow.backend.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@CrossOrigin("*")
public class DriverController {

    @Autowired
    private DriverService drive;

    @PostMapping("/addDriver")
    public Driver addDriver(@RequestBody Driver driver){
        return drive.saveDriver(driver);
    }

    @GetMapping
    public List<Driver> getAllDriver(){
        return drive.getAllDrivers();
    }

    @GetMapping("/available")
    public List<Driver> getAvailableDriver(){
        return drive.getAvailableDrivers();
    }

    @GetMapping("/bystatus")
    public List<Driver> getDriverByStats(@RequestParam DriverStatus stat){
        return drive.getDriverByStatus(stat);
    }

    @PutMapping("/{driverId}/assign/{vehicleId}")
    public String joinDriver(@PathVariable Long driverId, @PathVariable Long vehicleId){
        drive.assignVehicle(driverId, vehicleId);
        return "Driver Assigned to vehicle!";
    }


    @DeleteMapping("/{id}")
    public String deleteDriver(@PathVariable Long id){
        drive.deleteDriver(id);
        return "Driver Deleted";
    }


}
