package com.example.uspokajamlekbackend.user.patient.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
