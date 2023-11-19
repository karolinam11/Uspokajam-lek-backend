package com.example.uspokajamlekbackend.assignedExercise;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class AssignExerciseRequest {
    private Long doctorId;
    private Long patientId;
    private LocalDate dueDate;
    private Long exerciseId;

    @Override
    public String toString() {
        return "AssignExerciseRequest{" +
                "doctorId=" + doctorId +
                ", patientId=" + patientId +
                ", dueDate=" + dueDate +
                ", exerciseId=" + exerciseId +
                '}';
    }
}
