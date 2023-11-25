package com.example.uspokajamlekbackend.appointment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class AddAppointmentRequest {
    private Long doctorId;
    private Long patientId;
    private LocalDateTime visitStartDate;
    private LocalDateTime visitEndDate;
}
