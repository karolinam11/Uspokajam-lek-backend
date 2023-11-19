package com.example.uspokajamlekbackend.activity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ActivityResponse {
    private Long id;

    private String name;
    private LocalDate date;
    private String mood;
    private Long userId;

}
