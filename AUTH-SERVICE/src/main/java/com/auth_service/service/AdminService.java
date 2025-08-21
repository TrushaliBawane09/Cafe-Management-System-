package com.auth_service.service;

import com.auth_service.dto.*;
import com.auth_service.entities.User;
import com.auth_service.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {

    public ResponseEntity<List<User>> findAllByUserRole();

    public ResponseEntity<ApiResponse> updateStatus(int id , boolean status);

    public ResponseEntity<ApiResponse> changePassword(String email, ChangePassword changePassword);

    public ResponseEntity<ApiResponse> forgotPassword(String email);

    public ResponseEntity<ApiResponse> resetPassword(String email, ResetPassword resetPassword);

    public  Dashboard getDashboard();

    public ResponseEntity<ApiResponse> updateRole(int id, String role);

}
