package com.fleetflow.backend.service;

import com.fleetflow.backend.dto.DashboardSummaryDTO;
import com.fleetflow.backend.entity.BookingStatus;
import com.fleetflow.backend.entity.DriverStatus;
import com.fleetflow.backend.entity.VehicleStatus;
import com.fleetflow.backend.repository.BookingRepository;
import com.fleetflow.backend.repository.DriverRepository;
import com.fleetflow.backend.repository.VehicleRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    private final BookingRepository bookingRepo;
    private final DriverRepository driverRepo;
    private final VehicleRepository vehicleRepo;

    public DashboardService(BookingRepository bookingRepo, DriverRepository driverRepo, VehicleRepository vehicleRepo){
        this.bookingRepo = bookingRepo;
        this.driverRepo = driverRepo;
        this.vehicleRepo = vehicleRepo;
    }

    public DashboardSummaryDTO getDashboardSummary() {
        DashboardSummaryDTO dto = new DashboardSummaryDTO();

        dto.setTotalBookings(bookingRepo.count());

        dto.setPendingBookings(bookingRepo.countByStatus(BookingStatus.PENDING));

        dto.setInTransitBookings(bookingRepo.countByStatus(BookingStatus.IN_TRANSIT));

        dto.setDeliveredBookings(bookingRepo.countByStatus(BookingStatus.DELIVERED));

        dto.setCancelledBookings(bookingRepo.countByStatus(BookingStatus.CANCELLED));

        dto.setAvailableDrivers(driverRepo.countByStatus(DriverStatus.AVAILABLE));

        dto.setAvailableVehicles(vehicleRepo.countByStatus(VehicleStatus.AVAILABLE));

        return dto;
    }


}
