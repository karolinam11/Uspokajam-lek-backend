package com.example.uspokajamlekbackend.assignedExercise;

import com.example.uspokajamlekbackend.doctor.Doctor;
import com.example.uspokajamlekbackend.exercise.Exercise;
import com.example.uspokajamlekbackend.user.Patient;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity //encja, czyli bedzie zapis do bazy danych
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data //tworzy nam gettery i settery
public class AssignedExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Exercise exercise;
    private LocalDate dueDate;
    @ManyToOne(cascade = CascadeType.ALL)
    private Patient assignedTo;
    @ManyToOne(cascade = CascadeType.ALL)
    private Doctor assignedBy;

    @Enumerated(EnumType.STRING)
    private Status status;
    private boolean isDone;
}
