package com.auth_service.serviceimple;

import com.auth_service.dto.SignupRequest;
import com.auth_service.entities.User;
import com.auth_service.payload.ApiResponse;
import com.auth_service.repository.UserRepository;
import com.auth_service.service.UserService;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImple implements UserService {

}
