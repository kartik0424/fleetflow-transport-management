package com.fleetflow.backend.controller;

import com.fleetflow.backend.dto.DashboardSummaryDTO;
import com.fleetflow.backend.entity.BookingStatus;
import com.fleetflow.backend.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
public class DashboardController {

    private final DashboardService service;

    private DashboardController(DashboardService service){
        this.service = service;
    }

    @GetMapping("/summary")
    public DashboardSummaryDTO getSummary(){
        return service.getDashboardSummary();
    }

}
