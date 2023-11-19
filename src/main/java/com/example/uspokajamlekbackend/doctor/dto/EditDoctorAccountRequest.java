package com.example.uspokajamlekbackend.doctor.dto;

import com.example.uspokajamlekbackend.user.dto.EditAccountRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EditDoctorAccountRequest extends EditAccountRequest {
    private String specialization;
    private String address;
    private String phoneNumber;
}
