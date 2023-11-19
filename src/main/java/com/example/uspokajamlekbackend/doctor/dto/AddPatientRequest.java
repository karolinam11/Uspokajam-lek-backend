package com.example.uspokajamlekbackend.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddPatientRequest {
    private long doctorId;
    private long patientId;
}
