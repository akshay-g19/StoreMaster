package com.akshay.StoreMaster.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserRegistrationDTO {

    @NotBlank(message = "Name Cannot be blank")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email Format")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be 8-20 characters long")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Password must include at least one uppercase, one lowercase, one number, and one special character"
    )
    private String password;
}
