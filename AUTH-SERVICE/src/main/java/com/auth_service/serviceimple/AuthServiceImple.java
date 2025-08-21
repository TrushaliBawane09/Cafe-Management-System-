package com.auth_service.serviceimple;

import com.auth_service.JWT.CustomUserDetailsService;
import com.auth_service.JWT.JwtUtil;
import com.auth_service.dto.LoginRequest;
import com.auth_service.dto.PassingData;
import com.auth_service.dto.SignupRequest;
import com.auth_service.entities.User;
import com.auth_service.payload.ApiResponse;
import com.auth_service.repository.UserRepository;
import com.auth_service.service.AuthService;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AuthServiceImple implements AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImple.class);

    @Transactional
    @Override
    public ResponseEntity<ApiResponse> signUp(SignupRequest request) {
        try{
            Optional<User> userOptional = this.userRepository.findByEmail(request.getEmail());
            User user = new User();
            if(userOptional.isEmpty()){
                user.setName(StringUtils.isBlank(request.getName()) ? "" : request.getName());
                user.setEmail(StringUtils.isBlank(request.getEmail()) ? "" : request.getEmail());
                user.setContact(StringUtils.isBlank(request.getContact()) ? "" : request.getContact());
                if(!request.getPassword().equals(request.getConfirmPassword())){
                    return new ResponseEntity<>(new ApiResponse("Password and Confirm Password do not match !" , false, 400), HttpStatus.BAD_REQUEST);
                }

                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setRole("USER");
                user.setStatus(false);
                this.userRepository.save(user);
                return new ResponseEntity<>(new ApiResponse("Sign Up Successfully", true, 200), HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(new ApiResponse("Email already exist", false, 409),HttpStatus.CONFLICT );
            }

        }catch (Exception e){
            log.error("Exception while signup the User !!" +e);
            return new ResponseEntity<>(new ApiResponse("Internal Server Error", false, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> login(LoginRequest request) {
        try{
            //authentication means authenticate my username and password and check it present in DB if present then only generate the token
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            if(authentication.isAuthenticated()){
                UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
                String token = jwtUtil.generateToken(userDetails);
                String username = userDetails.getUsername();
                String role = userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .findFirst()
                        .orElse(null);

                Optional<User> userOptional= this.userRepository.findByEmail(username);
                User user = userOptional.get();

                boolean status = user.isStatus();

                PassingData passingData = new PassingData(token,username, role, status);

                if(!status && role.equals("ROLE_ADMIN")){
                    return  new ResponseEntity<>(new ApiResponse("Your account is inactive !!", false , 400), HttpStatus.BAD_REQUEST);
                }

                return  new ResponseEntity<>(new ApiResponse(passingData, true, 200), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ApiResponse("you are not authenticate" , false, 400),HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("Exception while login the User !!" +e);
            return new ResponseEntity<>(new ApiResponse("Something went wrong  ", false, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
