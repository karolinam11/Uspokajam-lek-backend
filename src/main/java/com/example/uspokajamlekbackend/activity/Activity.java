package com.example.uspokajamlekbackend.activity;

import com.example.uspokajamlekbackend.user.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity //encja, czyli bedzie zapis do bazy danych
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data //tworzy nam gettery i settery
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate date;
    private String mood;

    @ManyToOne
    private Patient patientActivity;

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", mood='" + mood + '\'' +
                '}';
    }
}
