package com.example.uspokajamlekbackend.user;

import com.example.uspokajamlekbackend.activity.Activity;
import com.example.uspokajamlekbackend.appointment.Appointment;
import com.example.uspokajamlekbackend.dailyReport.DailyReport;
import com.example.uspokajamlekbackend.doctor.Doctor;
import com.example.uspokajamlekbackend.assignedExercise.AssignedExercise;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name="users")
@Entity //encja, czyli bedzie zapis do bazy danych
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
@Data //tworzy nam gettery i settery
public class Patient {

    @Id
    @Column(name="user_id")
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


    @OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<AssignedExercise> assignedExercises;

    @OneToMany(mappedBy="patientActivity",cascade=CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Activity> activities;

    @OneToMany(mappedBy="patientDailyReport",cascade=CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<DailyReport> dailyReports;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "patient_doctor",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    @ToString.Exclude
    @JsonIgnore
    private List<Doctor> doctors = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "pending_request",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    @ToString.Exclude
    @JsonIgnore
    private List<Doctor> requests = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<Appointment> appointments;


}
