package com.example.uspokajamlekbackend.dailyReport;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Mood {
    private String name;
    private LocalDate date;
}
