package com.example.uspokajamlekbackend.dailyReport;

import com.example.uspokajamlekbackend.dailyReport.dto.DailyReportRequest;
import com.example.uspokajamlekbackend.user.patient.PatientService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Log4j2
public class DailyReportService {

    @Autowired
    private DailyReportRepository dailyReportRepository;
    @Autowired
    private PatientService patientService;

    @Autowired
    private ModelMapper modelMapper;

    public List<DailyReport> getUserDailyReports(Long userId) {
        return dailyReportRepository.getDailyReportsByPatientDailyReportIdOrderByDate(userId);
    }

    public void addDailyReport(DailyReportRequest dailyReportRequest) {
        DailyReport dailyReport = modelMapper.map(dailyReportRequest, DailyReport.class);
        dailyReport.setPatientDailyReport(patientService.getById(dailyReportRequest.getUserId()));
        dailyReportRepository.save(dailyReport);
    }

    public boolean checkIfDailyReportCanBeAdded(LocalDate localDate, long userId) {
        return dailyReportRepository.countAllByDateAndPatientDailyReportId(localDate, userId) < 1;
    }

    public List<Mood> getMoods(int numOfDays, long userId) {
        List<DailyReport> reports = dailyReportRepository.getAllByPatientDailyReportId(userId);
        LocalDate dateDaysBefore = LocalDate.now().minusDays(numOfDays);
        List<Mood> moods = reports.stream().map((report -> new Mood(report.getMood(), report.getDate()))).toList();
        return moods.stream().filter((mood -> mood.getDate().isAfter(dateDaysBefore))).toList();
    }

    public List<Integer> getMoodsQuantityInLastXDays(long userId, int lastXDays) {
        LocalDate date = LocalDate.now().minusDays(lastXDays);
        List<DailyReport> reports = dailyReportRepository.getAllByPatientDailyReportIdAndDate(userId,date);
        int terrible = (int) reports.stream().filter(report -> report.getMood().equals("terrible")).count();
        int bad = (int) reports.stream().filter(report -> report.getMood().equals("bad")).count();
        int neutral = (int) reports.stream().filter(report -> report.getMood().equals("neutral")).count();
        int good = (int) reports.stream().filter(report -> report.getMood().equals("good")).count();
        int excellent = (int) reports.stream().filter(report -> report.getMood().equals("excellent")).count();
        return List.of(terrible, bad, neutral, good, excellent);
    }

    public DailyReport getById(long id) {
        return this.dailyReportRepository.getById(id);
    }

    public void removeReport(long id) {
        this.dailyReportRepository.delete(this.getById(id));
    }
}