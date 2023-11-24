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
//    private List<DoctorResponse> doctors;

//    public static PatientResponse createPatientResponse(Patient patient) {
//        return PatientResponse.builder()
//                .id(patient.getId())
//                .name(patient.getName())
//                .email(patient.getEmail())
//                .birthDate(patient.getBirthDate())
//                .surname(patient.getSurname())
//                .role(patient.getRole())
//                .build();
//    }
}
