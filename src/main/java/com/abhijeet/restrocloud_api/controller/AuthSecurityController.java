package com.abhijeet.restrocloud_api.controller;

import com.abhijeet.restrocloud_api.dto.ApiResponse;
import com.abhijeet.restrocloud_api.dto.request.*;
import com.abhijeet.restrocloud_api.dto.response.JwtResponseDTO;
import com.abhijeet.restrocloud_api.security.service.JwtService;
import com.abhijeet.restrocloud_api.service.OtpEmailAuthService;
import com.abhijeet.restrocloud_api.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthSecurityController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private OtpEmailAuthService otpEmailAuthService;

    //Register a resturent use for signUp
    @PostMapping("/signUp")
    public ResponseEntity<ApiResponse<?>> signUp(@Valid @RequestBody RestaurantRequestDTO dto){
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Restaurant registered successfully")
                .data(restaurantService.signUp(dto))
                .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginRequestDTO loginRequestDTO){

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getEmail(),
                            loginRequestDTO.getPassword()
                    )
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    ApiResponse.builder()
                            .success(false)
                            .message("Incorrect username or password")
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }

        String token = jwtService.generateToken(loginRequestDTO.getEmail());

        JwtResponseDTO jwtResponse = JwtResponseDTO.builder()
                .token(token)
                .email(loginRequestDTO.getEmail())
                .build();

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Login successful")
                        .data(jwtResponse)
                        .build()
        );
    }

    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse<?>> sendOtp(@Valid @RequestBody GenerateOtpRequest generateOtpRequest){

        boolean isSent = otpEmailAuthService.generateAndSendOTP(generateOtpRequest);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(isSent)
                        .message(isSent ? "OTP Sent Successfully" : "Failed to send OTP")
                        .data(null)
                        .build()
        );
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<?>> verifyOtp(@Valid @RequestBody OtpRequest otpRequest){

        boolean isVerify = otpEmailAuthService.verifyOTP(otpRequest);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(isVerify)
                        .message(isVerify ? "Otp verifyed Successfully": "Wrong OTP")
                        .data(null)
                        .build()
        );
    }

    @PostMapping("/changePassword")
    public ResponseEntity<ApiResponse<?>> passwordChange(@Valid @RequestBody PasswordChangeDTO passwordChangeDTO){
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(restaurantService.changePassword(passwordChangeDTO))
                        .message("Password changed successfull. Login again")
                        .data(null)
                        .build()
        );

    }
}
