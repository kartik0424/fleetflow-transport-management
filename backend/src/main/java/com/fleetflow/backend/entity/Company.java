package com.fleetflow.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String companyName;

    @Column(nullable = false, unique = true)
    @Email(message = "Please provide a valid email address",
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    private String email;

    @Column(nullable = false, unique = true)
    private String phoneNo;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    private CompanyStatus status;

    private LocalDateTime createdAt;
    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();

        if(this.status == null){
            this.status = CompanyStatus.ACTIVE;
        }
    }


}
