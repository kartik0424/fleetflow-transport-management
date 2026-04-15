package com.fleetflow.backend.service;

import com.fleetflow.backend.entity.Vehicle;
import com.fleetflow.backend.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VehicleService {
    @Autowired
    private VehicleRepository repo;

    public List<Vehicle> getAllVehicles(){
        return repo.findAll();
    }
    public Vehicle saveVehicle(Vehicle vehicle){
        return repo.save(vehicle);
    }

    public void deleteVehicle(Long id){
        repo.deleteById(id);
    }
}
