package com.fleetflow.backend.controller;

import com.fleetflow.backend.dto.auth.RegisterRequestDTO;
import com.fleetflow.backend.entity.User;
import com.fleetflow.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {
    private final AuthService authserv;
    public AuthController(AuthService authserv){
        this.authserv = authserv;
    }
    @PostMapping("register")
    public String registerCompany(
            @Valid @RequestBody RegisterRequestDTO request
            ){
        return authserv.RegisterCompany(request);
    }
}
