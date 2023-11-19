package com.example.uspokajamlekbackend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddDoctorRequest {
    private long patientId;
    private String invitationCode;

}
