package com.auth_service.controller;

import com.auth_service.dto.LoginRequest;
import com.auth_service.dto.SignupRequest;
import com.auth_service.payload.ApiResponse;
import com.auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signUp(@RequestBody(required = false) SignupRequest request){
        if(request == null){
            return new ResponseEntity<>(new ApiResponse("Invalid user data. Please check your input and try again", false, 400), HttpStatus.BAD_REQUEST);
        }
        return  this.authService.signUp(request);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        if(request == null){
            return new ResponseEntity<>(new ApiResponse("request is null", false, 400), HttpStatus.BAD_REQUEST);
        }
        return this.authService.login(request);
    }

    @GetMapping
    public String authTest(){
        return "auth testing";
    }
}
