package com.example.uspokajamlekbackend.doctor.dto;

import com.example.uspokajamlekbackend.user.dto.SignupRequest;

public class DoctorSignupRequest extends SignupRequest {
    private String specialization;
    private String address;
    private String phoneNumber;
}
