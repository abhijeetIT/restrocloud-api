package com.abhijeet.restrocloud_api.service.impl;

import com.abhijeet.restrocloud_api.dto.request.GenerateOtpRequest;
import com.abhijeet.restrocloud_api.dto.request.OtpRequest;
import com.abhijeet.restrocloud_api.entity.OtpEmailAuth;
import com.abhijeet.restrocloud_api.exception.ResourceNotFoundException;
import com.abhijeet.restrocloud_api.repository.OtpEmailAuthRepository;
import com.abhijeet.restrocloud_api.repository.RestaurantRepository;
import com.abhijeet.restrocloud_api.service.OtpEmailAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OtpEmailAuthServiceImpl implements OtpEmailAuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OtpEmailAuthRepository otpEmailAuthRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Value("${brevo.api.key}")
    private String brevoApiKey;

    @Value("${brevo.api.url}")
    private String brevoApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // 🔐 OTP generator
    public static String generateOtp() {
        SecureRandom secureRandom = new SecureRandom();
        int otp = 1000 + secureRandom.nextInt(9000);
        return String.valueOf(otp);
    }

    // ================= SEND OTP =================
    @Transactional
    @Override
    public Boolean generateAndSendOTP(GenerateOtpRequest request) {

        String email = request.getEmail();
        String purpose = request.getPurpose().toUpperCase();

        // ✅ FIXED LOGIC
        if (purpose.equals("SIGNUP")) {
            if (restaurantRepository.existsByEmail(email)) {
                throw new RuntimeException("Account already exists with this email");
            }
        } else {
            if (!restaurantRepository.existsByEmail(email)) {
                throw new ResourceNotFoundException("No account found with email " + email);
            }
        }

        try {
            // 🔁 Delete old OTP
            otpEmailAuthRepository.deleteByEmail(email);

            // 🔐 Generate OTP
            String otp = generateOtp();

            // 🎯 Purpose message
            String actionText = switch (purpose) {
                case "SIGNUP" -> "complete your Sign Up";
                case "FORGOT_PASSWORD" -> "reset your password";
                case "LOGIN" -> "verify your login";
                default -> "verify your action";
            };

            // 💌 Email HTML
            String html = """
<html>
<body style="font-family:Arial;">
    <h2>RestroCloud OTP</h2>
    <p>Use this OTP to %s</p>
    <h1>%s</h1>
    <p>This OTP is valid for 2 minutes.</p>
</body>
</html>
""".formatted(actionText, otp);

            // 🔐 Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("api-key", brevoApiKey);

            // 📦 Request Body
            Map<String, Object> body = new HashMap<>();

            Map<String, String> sender = new HashMap<>();
            sender.put("name", "RestroCloud");
            sender.put("email", "abhijha4324@gmail.com"); // MUST be verified

            body.put("sender", sender);

            Map<String, String> toUser = new HashMap<>();
            toUser.put("email", email);

            body.put("to", List.of(toUser));
            body.put("subject", "RestroCloud | OTP Verification");
            body.put("htmlContent", html);

            HttpEntity<Map<String, Object>> httpRequest =
                    new HttpEntity<>(body, headers);

            // Call Brevo API
            ResponseEntity<String> response = restTemplate.postForEntity(
                    brevoApiUrl,
                    httpRequest,
                    String.class
            );

            // Optional: check response
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to send email via Brevo");
            }

            // 💾 Save OTP (hashed)
            otpEmailAuthRepository.save(
                    OtpEmailAuth.builder()
                            .email(email)
                            .purpose(purpose)
                            .otp(passwordEncoder.encode(otp))
                            .expiresAt(LocalDateTime.now().plusMinutes(2))
                            .build()
            );

            return true;

        } catch (Exception e) {
            e.printStackTrace(); // 🔥 IMPORTANT for debugging
            throw new RuntimeException("Error sending OTP: " + e.getMessage());
        }
    }

    // ================= VERIFY OTP =================
    @Transactional
    @Override
    public Boolean verifyOTP(OtpRequest request) {

        Optional<OtpEmailAuth> optional =
                otpEmailAuthRepository.findByEmail(request.getEmail());

        if (optional.isEmpty()) {
            return false;
        }

        OtpEmailAuth otpEntity = optional.get();

        if (otpEntity.getExpiresAt().isBefore(LocalDateTime.now())) {
            otpEmailAuthRepository.delete(otpEntity);
            return false;
        }

        if (!passwordEncoder.matches(request.getOtp(), otpEntity.getOtp())) {
            return false;
        }

        otpEmailAuthRepository.delete(otpEntity);
        return true;
    }
}