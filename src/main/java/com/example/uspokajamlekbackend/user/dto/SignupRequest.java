package com.example.uspokajamlekbackend.user.dto;

import com.example.uspokajamlekbackend.doctor.dto.DoctorResponse;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SignupRequest {

    private String email;
    private String password;
    private String name;
    private String surname;
    private LocalDate birthDate;

    private List<DoctorResponse> doctors;

    private String role;
    private String specialization;
    private String address;
    private String phoneNumber;
}
