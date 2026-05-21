package com.fleetflow.backend.service;

import com.fleetflow.backend.dto.auth.RegisterRequestDTO;
import com.fleetflow.backend.entity.Company;
import com.fleetflow.backend.entity.CompanyStatus;
import com.fleetflow.backend.entity.User;
import com.fleetflow.backend.entity.UserRole;
import com.fleetflow.backend.repository.CompanyRepository;
import com.fleetflow.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepo;
    private final CompanyRepository companyRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepo, PasswordEncoder passwordEncoder, CompanyRepository companyRepo){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.companyRepo = companyRepo;
    }

    public String RegisterCompany(RegisterRequestDTO request){
        if(userRepo.existsByUsername(request.getUsername())){
            throw new RuntimeException("Username already exists!");
        }

        if(companyRepo.existsByCompanyName(request.getCompanyName())){
            throw new RuntimeException("Company already exists!");
        }

        if(companyRepo.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already Exist");
        }

        Company company = new Company();

        company.setCompanyName(request.getCompanyName());
        company.setEmail(request.getEmail());
        company.setPhoneNo(request.getPhoneNo());
        company.setAddress((request.getAddress()));
        company.setStatus(CompanyStatus.ACTIVE);

        Company savedCompany = companyRepo.save(company);

        User user = new User();

        user.setUsername(request.getUsername());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole(UserRole.ADMIN);
        user.setCompany(savedCompany);

        userRepo.save(user);

        return "Company Registered Successfully";

    }



}
