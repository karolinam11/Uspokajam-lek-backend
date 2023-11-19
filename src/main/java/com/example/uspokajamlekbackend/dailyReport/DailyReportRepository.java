package com.example.uspokajamlekbackend.dailyReport;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DailyReportRepository extends JpaRepository<DailyReport, Long> {

    List<DailyReport> getDailyReportsByPatientDailyReportId(Long patientDailyReportId);

    int countAllByDateAndPatientDailyReportId(LocalDate date, Long patientDailyReportId);

    List<DailyReport> getAllByPatientDailyReportId(Long patientDailyReportId);

}
