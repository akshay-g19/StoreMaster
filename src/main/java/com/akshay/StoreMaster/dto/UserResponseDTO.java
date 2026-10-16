package com.akshay.StoreMaster.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
}
