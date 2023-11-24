package com.example.uspokajamlekbackend.exercise;

import com.example.uspokajamlekbackend.user.doctor.Doctor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ExerciseResponse {
    private Long id;
    private String name;
    private String description;
    private String category;
    private String duration;
    private Doctor createdBy;

    public static ExerciseResponse createExerciseResponse(Exercise exercise){
        return ExerciseResponse.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .createdBy(exercise.getCreatedBy())
                .category(exercise.getCategory())
                .description(exercise.getDescription())
                .duration(exercise.getDuration())
                .build();
    }
}
