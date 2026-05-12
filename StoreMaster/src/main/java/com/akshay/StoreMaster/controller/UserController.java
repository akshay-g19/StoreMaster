package com.akshay.StoreMaster.controller;

import com.akshay.StoreMaster.dto.UserLoginDTO;
import com.akshay.StoreMaster.dto.UserRegistrationDTO;
import com.akshay.StoreMaster.exception.UserAlreadyExistException;
import com.akshay.StoreMaster.repository.UserRepository;
import com.akshay.StoreMaster.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/storeMaster/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String inService(){
        return "Hello from StoreMaster Service";
    }

    @PostMapping("/register")
    public ResponseEntity<String> addUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) throws Exception{
        try {
            userService.registerUser(userRegistrationDTO);
            return ResponseEntity.ok("Registration Successfully Completed for User: " + userRegistrationDTO.getName());
        } catch (IllegalArgumentException e) {
            throw new UserAlreadyExistException(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO) throws Exception{
        userService.login(userLoginDTO);
        return ResponseEntity.ok("Logged in Successfully");
    }
}
