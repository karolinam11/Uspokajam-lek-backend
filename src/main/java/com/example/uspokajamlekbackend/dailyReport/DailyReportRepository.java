package com.example.uspokajamlekbackend.dailyReport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DailyReportRepository extends JpaRepository<DailyReport, Long> {

    List<DailyReport> getDailyReportsByPatientDailyReportIdOrderByDate(Long patientDailyReportId);

    int countAllByDateAndPatientDailyReportId(LocalDate date, Long patientDailyReportId);

    List<DailyReport> getAllByPatientDailyReportId(Long patientDailyReportId);

    @Query("FROM DailyReport d WHERE d.date > :date AND d.patientDailyReport.id = :patientDailyReportId")
    List<DailyReport> getAllByPatientDailyReportIdAndDate(Long patientDailyReportId, LocalDate date);

}
