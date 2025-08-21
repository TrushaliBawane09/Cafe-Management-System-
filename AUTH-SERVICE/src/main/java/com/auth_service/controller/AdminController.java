package com.auth_service.controller;

import com.auth_service.JWT.JwtUtil;
import com.auth_service.dto.*;
import com.auth_service.entities.User;
import com.auth_service.payload.ApiResponse;
import com.auth_service.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser(){
      return  this.adminService.findAllByUserRole();
    }

    @PutMapping("/status/{id}/{status}")
    public ResponseEntity<ApiResponse> updateStatus(@PathVariable int id, @PathVariable("status") boolean status){
      return  this.adminService.updateStatus(id, status);
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse> changePassword(@RequestBody ChangePassword changePassword){
        String email = jwtUtil.getCurrentUser().getUsername();
       return this.adminService.changePassword(email,changePassword);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> getOTP(@RequestBody ForgotPassword forgotPassword){
    String email = forgotPassword.getEmail();
      return  this.adminService.forgotPassword(email);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam("email") String email, @RequestBody ResetPassword resetPassword){
       return this.adminService.resetPassword(email, resetPassword);
    }
    @GetMapping("test-admin")
    public String testAdmin(){
        return "test  admin";
    }

    @GetMapping("/counts")
    public Dashboard  getDashboardDetails(){
        return  this.adminService.getDashboard();
    }

    @PutMapping("/update-role/{id}/{role}")
    public ResponseEntity<ApiResponse> updateRole(@PathVariable int id , @PathVariable String role){
       return this.adminService.updateRole(id, role);
    }
}
