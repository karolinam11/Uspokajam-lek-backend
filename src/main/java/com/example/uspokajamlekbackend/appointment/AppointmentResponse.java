package com.example.uspokajamlekbackend.appointment;

import com.example.uspokajamlekbackend.doctor.dto.DoctorResponse;
import com.example.uspokajamlekbackend.user.dto.PatientResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentResponse {
    private Long id;
    private LocalDateTime visitStartDate;
    private LocalDateTime visitEndDate;
    private DoctorResponse doctor;
    private PatientResponse patient;
}
