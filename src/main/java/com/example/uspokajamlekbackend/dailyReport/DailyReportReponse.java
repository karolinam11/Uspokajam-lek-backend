package com.example.uspokajamlekbackend.dailyReport;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DailyReportReponse {
    private Long id;
    private LocalDate date;
    private String mood;
    private String note;

    private Long userId;


}
