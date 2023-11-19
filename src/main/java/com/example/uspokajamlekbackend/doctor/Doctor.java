package com.example.uspokajamlekbackend.doctor;

import com.example.uspokajamlekbackend.appointment.Appointment;
import com.example.uspokajamlekbackend.assignedExercise.AssignedExercise;
import com.example.uspokajamlekbackend.exercise.Exercise;
import com.example.uspokajamlekbackend.user.Role;
import com.example.uspokajamlekbackend.user.Patient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "doctors")
@ToString
public class Doctor {
    @Id
    @Column(name = "doctor_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private String email;
    @NonNull
    private String password;
    private String name;
    private String surname;
    private LocalDate birthDate;
    @NonNull
    @Enumerated(EnumType.STRING)
    private Role role;

    private String specialization;
    private String address;

    private String phoneNumber;

    private String invitationCode;

    @ManyToMany(mappedBy = "doctors", fetch = FetchType.EAGER, cascade ={CascadeType.MERGE,CascadeType.PERSIST})
    @ToString.Exclude
    @JsonIgnore
    private List<Patient> patients = new ArrayList<>();

    @ManyToMany(mappedBy = "requests", fetch = FetchType.EAGER, cascade ={CascadeType.MERGE,CascadeType.PERSIST})
    @ToString.Exclude
    @JsonIgnore
    private List<Patient> pendingRequests = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "assignedBy", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<AssignedExercise> exercisesAssignedByUser;
}
