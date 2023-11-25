package com.example.uspokajamlekbackend.dailyReport.dto;

import lombok.Data;

@Data
public class MoodsRequest {
    private int numOfDays;
    private long userId;
}
