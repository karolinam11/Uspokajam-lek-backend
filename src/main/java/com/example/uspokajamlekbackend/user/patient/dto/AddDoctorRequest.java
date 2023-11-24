package com.example.uspokajamlekbackend.user.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddDoctorRequest {
    private long patientId;
    private String invitationCode;

}
