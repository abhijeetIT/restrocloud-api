package com.abhijeet.restrocloud_api.service;

import com.abhijeet.restrocloud_api.dto.request.GenerateOtpRequest;
import com.abhijeet.restrocloud_api.dto.request.OtpRequest;

public interface OtpEmailAuthService {
    Boolean generateAndSendOTP(GenerateOtpRequest generateOtpRequest);
    Boolean verifyOTP(OtpRequest otpRequest);
}
