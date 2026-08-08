package com.akshay.StoreMaster.controller;

import com.akshay.StoreMaster.dto.UserLoginDTO;
import com.akshay.StoreMaster.dto.UserRegistrationDTO;
import com.akshay.StoreMaster.dto.UserResponseDTO;
import com.akshay.StoreMaster.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class UserController{

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRegistrationDTO dto){
        UserResponseDTO created = userService.registerUser(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        log.info("User registered successfully: {}", created.getId());
        return ResponseEntity.created(location).body(created);
    }

    //Future Enhancement: Implement JWT token generation and return token in response for login
    //It will return AuthResponseDTO
    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO){
        userService.login(userLoginDTO);
        return ResponseEntity.ok("Logged in Successfully");
    }
}
