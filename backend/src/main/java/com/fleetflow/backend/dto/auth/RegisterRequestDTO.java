package com.fleetflow.backend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

    @NotBlank
    private String companyName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String phoneNo;

    @NotBlank
    private String address;

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 8, max = 12)
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "Password must contain uppercase, lowercase, number and special character"
    )
    private String password;
}
