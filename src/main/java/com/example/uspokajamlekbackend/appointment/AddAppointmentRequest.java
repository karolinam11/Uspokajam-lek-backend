package com.example.uspokajamlekbackend.appointment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class AddAppointmentRequest {
    private Long doctorId;
    private Long patientId;
    private LocalDateTime visitStartDate;
    private LocalDateTime visitEndDate;
}
