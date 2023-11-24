package com.example.uspokajamlekbackend.user.patient.dto;

import com.example.uspokajamlekbackend.user.patient.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EditAccountRequest {
    private String email;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private Role role;
}
