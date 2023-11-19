package com.example.uspokajamlekbackend.user.dto;

import com.example.uspokajamlekbackend.user.Role;
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
