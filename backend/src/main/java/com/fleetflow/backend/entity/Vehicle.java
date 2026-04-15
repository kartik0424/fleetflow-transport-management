package com.fleetflow.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brandName;

    @Column(unique = true, nullable = false)
    private String vehicleNumber;

    @Column(nullable = false)
    private String vehicleType;

    @NotNull
    @Positive
    private Double capacity;

    @Enumerated(EnumType.STRING)
    private VehicleStatus status;

    private String fuelType;

    @Column(nullable = false)
    private LocalDate insuranceExpiry;

    private LocalDate lastServiceDate;
}
