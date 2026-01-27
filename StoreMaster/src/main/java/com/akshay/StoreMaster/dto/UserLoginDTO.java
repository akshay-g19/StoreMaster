package com.akshay.StoreMaster.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
//@Data
//public class UserLoginDTO {
//
//    @Email
//    private String email;
//    @NotBlank
//    private String password;
//}

public record UserLoginDTO ( String email, String password) {

}
