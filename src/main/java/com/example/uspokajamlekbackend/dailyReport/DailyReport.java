package com.example.uspokajamlekbackend.dailyReport;

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
public class DailyReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String mood;
    private String note;

    @ManyToOne
    private Patient patientDailyReport;

    @Override
    public String toString() {
        return "DailyReport{" +
                "id=" + id +
                ", date=" + date +
                ", mood='" + mood + '\'' +
                ", note='" + note + '\'' +
                ", userDailyReport=" + patientDailyReport +
                '}';
    }
}
