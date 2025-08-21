package com.auth_service.serviceimple;

import com.auth_service.circuitBreaker.BillServiceCaller;
import com.auth_service.circuitBreaker.CategoryServiceCaller;
import com.auth_service.circuitBreaker.ProductServiceCaller;
import com.auth_service.dto.*;
import com.auth_service.entities.User;
import com.auth_service.extservices.BillServiceExt;
import com.auth_service.extservices.CategoryExtService;
import com.auth_service.extservices.ProductExtService;
import com.auth_service.payload.ApiResponse;
import com.auth_service.repository.UserRepository;
import com.auth_service.service.AdminService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminServiceImple implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private  CategoryExtService categoryExtService;

    @Autowired
    private  ProductExtService productExtService;

    @Autowired
    private BillServiceExt billServiceExt;

    @Autowired
    private CategoryServiceCaller categoryServiceCaller;

    @Autowired
    private ProductServiceCaller productServiceCaller;

    @Autowired
    private BillServiceCaller billServiceCaller;

    @Override
    public ResponseEntity<List<User>> findAllByUserRole() {
        List<User> userList = this.userRepository.findAll();
        List<User> filteredUsers = userList.stream()
                .filter(user -> !"SUPERADMIN".equals(user.getRole())) // exclude SUPERADMIN
                .collect(Collectors.toList());

        return new ResponseEntity<>(userList.isEmpty() ? Collections.emptyList() : filteredUsers, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<ApiResponse> updateStatus(int id, boolean status) {
       Optional<User> userOptional = this.userRepository.findById(id);
       if(userOptional.isEmpty()){
           return new ResponseEntity<>(new ApiResponse("User not found", false, 404),HttpStatus.NOT_FOUND);
       }
       User user = userOptional.get();
       user.setStatus(status);
        return new ResponseEntity<>(new ApiResponse("status updated successfully" , true, 200), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> changePassword(String email , ChangePassword changePassword) {
       Optional<User> userOptional = this.userRepository.findByEmail(email);
       if(userOptional.isEmpty()){
           return new ResponseEntity<>(new ApiResponse("User not exist", false, 404), HttpStatus.NOT_FOUND);
       }
       User user = userOptional.get();
       if(passwordEncoder.matches(changePassword.getOldPassword(), user.getPassword())){
           user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
           this.userRepository.save(user);
           return new ResponseEntity<>(new ApiResponse("password changes successfully ", true, 200), HttpStatus.OK);
       }else{
           return new ResponseEntity<>(new ApiResponse("old password is incorrect ", false, 400), HttpStatus.BAD_REQUEST);
       }
    }

    @Override
    public ResponseEntity<ApiResponse> forgotPassword(String email) {
        Optional<User> userOptional =  this.userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
           return new ResponseEntity<>(new ApiResponse("This email does not exist", false, 400), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User user = userOptional.get();
        String otp = generateOtp();
        sendOtpToEmail(email,otp);
        return new ResponseEntity<>(new ApiResponse("OTP send to your email account ", true, 200), HttpStatus.OK);
    }

    public  String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public  void sendOtpToEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp);
        mailSender.send(message);
    }

    @Override
    public ResponseEntity<ApiResponse> resetPassword(String email, ResetPassword resetPassword) {
        Optional<User> userOptional = this.userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            return new ResponseEntity<>(new ApiResponse("this user not exist", false, 400), HttpStatus.BAD_REQUEST);
        }
        User user = userOptional.get();
        if(!resetPassword.getNewPassword().equals(resetPassword.getConfirmNewPassword())){
            return new ResponseEntity<>(new ApiResponse("password are not match ", false, 400), HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(resetPassword.getNewPassword()));
        this.userRepository.save(user);
        return new ResponseEntity<>(new ApiResponse("password reset successfully ", true, 200), HttpStatus.OK);
    }

    @Override
    public Dashboard getDashboard() {
        Dashboard dashboard = new Dashboard();
        TotalCategory totalCategory =this.categoryServiceCaller.getTotalcategory();
        TotalProduct totalProduct = this.productServiceCaller.getTotalproduct();
        TotalBill totalBill = this.billServiceCaller.getTotalBill();

        dashboard.setTotalCategory(totalCategory.getTotalCategory());
        dashboard.setTotalproduct(totalProduct.getTotalProduct());
        dashboard.setTotalBill(totalBill.getTotalBill());
        return dashboard;
    }

    @Transactional
    @Override
    public ResponseEntity<ApiResponse> updateRole(int id, String role) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if(userOptional.isEmpty()){
            return new ResponseEntity<>(new ApiResponse("object is null",false, 400), HttpStatus.BAD_REQUEST);
        }
        User user = userOptional.get();
        user.setRole(role);
        this.userRepository.save(user);
        return new ResponseEntity<>(new ApiResponse("role updated ", true, 200), HttpStatus.OK);
    }
}
