package com.fleetflow.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DashboardSummaryDTO {
    private Long totalBookings;
    private Long pendingBookings;
    private Long inTransitBookings;
    private Long deliveredBookings;
    private Long cancelledBookings;
    private Long availableDrivers;
    private Long availableVehicles;

}
