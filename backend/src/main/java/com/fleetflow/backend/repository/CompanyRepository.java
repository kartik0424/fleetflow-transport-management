package com.fleetflow.backend.repository;

import com.fleetflow.backend.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>{
    boolean existsByEmail(String email);

    boolean existsByCompanyName(String companyName);
}
