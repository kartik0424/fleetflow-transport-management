package com.fleetflow.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(unique = true, nullable = false)
    private String licenseNumber;

    @Column(nullable = false)
    private LocalDate licenseExpiry;

    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    @ManyToOne
    @JoinColumn(name = "vehicle_id") //may give error
    private Vehicle vehicle;

    private LocalDate createdAt;
    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDate.now();

        if(this.status == null){
            this.status = DriverStatus.AVAILABLE;
        }
    }
}
