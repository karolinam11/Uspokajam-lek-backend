package com.example.uspokajamlekbackend.user.dto;

import com.example.uspokajamlekbackend.doctor.dto.DoctorResponse;
import com.example.uspokajamlekbackend.user.Patient;
import com.example.uspokajamlekbackend.user.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class PatientResponse {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private Role role;
    private List<DoctorResponse> doctors;

    public static PatientResponse createPatientResponse(Patient patient) {
        return PatientResponse.builder()
                .id(patient.getId())
                .name(patient.getName())
                .email(patient.getEmail())
                .birthDate(patient.getBirthDate())
                .surname(patient.getSurname())
                .role(patient.getRole())
                .build();
    }
}
