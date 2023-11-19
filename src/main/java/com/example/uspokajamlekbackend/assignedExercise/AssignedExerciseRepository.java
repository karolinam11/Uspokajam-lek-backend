package com.example.uspokajamlekbackend.assignedExercise;

import com.example.uspokajamlekbackend.exercise.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssignedExerciseRepository extends JpaRepository<AssignedExercise, Long> {

    List<AssignedExercise> getAssignedExerciseByAssignedToId(Long patientId);

    List<AssignedExercise> getAssignedExerciseByAssignedToIdAndStatus(Long patientId, Status status);

}

