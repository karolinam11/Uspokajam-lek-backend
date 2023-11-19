package com.example.uspokajamlekbackend.doctor.dto;

import com.example.uspokajamlekbackend.doctor.Doctor;
import com.example.uspokajamlekbackend.user.dto.PatientResponse;
import com.example.uspokajamlekbackend.user.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class DoctorResponse {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String specialization;
    private String address;
    private String phoneNumber;
    private Role role;
    private String invitationCode;
    private List<PatientResponse> patients;

    public static DoctorResponse createDoctorResponse(Doctor doctor) {
        return DoctorResponse.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .email(doctor.getEmail())
                .birthDate(doctor.getBirthDate())
                .surname(doctor.getSurname())
                .specialization(doctor.getSpecialization())
                .phoneNumber(doctor.getPhoneNumber())
                .address(doctor.getAddress())
                .role(doctor.getRole())
                .invitationCode(doctor.getInvitationCode())
                .patients(doctor.getPatients().stream().map(
                        user -> PatientResponse.builder()
                                .id(user.getId())
                                .name(user.getName())
                                .surname(user.getSurname())
                                .email(user.getEmail())
                                .birthDate(user.getBirthDate())
                                .build()).toList())
                .build();
    }
}
