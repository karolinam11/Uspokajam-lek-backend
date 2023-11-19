package com.example.uspokajamlekbackend.appointment;

import com.example.uspokajamlekbackend.doctor.Doctor;
import com.example.uspokajamlekbackend.user.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data //tworzy nam gettery i settery
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private LocalDateTime visitStartDate;

    @NonNull
    private LocalDateTime visitEndDate;
    @NonNull
    @ManyToOne
    private Patient patient;
    @NonNull
    @ManyToOne
    private Doctor doctor;

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", visitDate=" + visitStartDate +
                '}';
    }
}
