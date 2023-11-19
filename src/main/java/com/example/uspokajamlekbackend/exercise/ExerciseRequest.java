package com.example.uspokajamlekbackend.exercise;

import lombok.Data;

@Data
public class ExerciseRequest {

    private Long id;
    private String name;
    private String description;
    private String category;
    private Long createdBy;
    private String duration;
}
