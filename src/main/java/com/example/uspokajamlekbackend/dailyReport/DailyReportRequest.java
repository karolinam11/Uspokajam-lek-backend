package com.example.uspokajamlekbackend.dailyReport;

import lombok.Data;

import java.time.LocalDate;

@Data

public class DailyReportRequest {
    private LocalDate date;
    private String mood;
    private String note;
    private Long userId;

}
