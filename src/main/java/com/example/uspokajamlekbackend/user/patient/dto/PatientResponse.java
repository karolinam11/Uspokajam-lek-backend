package com.example.uspokajamlekbackend.user.patient.dto;

import com.example.uspokajamlekbackend.user.doctor.dto.DoctorResponse;
import com.example.uspokajamlekbackend.user.patient.Patient;
import com.example.uspokajamlekbackend.user.patient.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PatientResponse {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private Role role;

}
