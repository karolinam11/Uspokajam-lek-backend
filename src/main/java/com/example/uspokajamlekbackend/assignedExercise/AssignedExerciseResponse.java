package com.example.uspokajamlekbackend.assignedExercise;

import com.example.uspokajamlekbackend.doctor.Doctor;
import com.example.uspokajamlekbackend.doctor.dto.DoctorResponse;
import com.example.uspokajamlekbackend.exercise.Exercise;
import com.example.uspokajamlekbackend.exercise.ExerciseResponse;
import com.example.uspokajamlekbackend.user.Patient;
import com.example.uspokajamlekbackend.user.dto.PatientResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class AssignedExerciseResponse {
    private Long id;
    private ExerciseResponse exercise;
    private LocalDate dueDate;
    private PatientResponse assignedTo;
    private DoctorResponse assignedBy;
    private Status status;
    private boolean isDone;
}
