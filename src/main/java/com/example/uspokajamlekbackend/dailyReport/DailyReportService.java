package com.example.uspokajamlekbackend.dailyReport;

import com.example.uspokajamlekbackend.user.PatientService;
import lombok.extern.log4j.Log4j2;
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

    public List<DailyReportReponse> getUserDailyReports(Long userId){
        return dailyReportRepository.getDailyReportsByPatientDailyReportId(userId).stream().map(
                dailyReport -> new DailyReportReponse(
                        dailyReport.getId(),
                        dailyReport.getDate(),
                        dailyReport.getMood(),
                        dailyReport.getNote(),
                        dailyReport.getPatientDailyReport().getId()
                        )
        ).toList();
    }

    public void addDailyReport(DailyReportRequest dailyReportRequest){
        dailyReportRepository.save(
                DailyReport.builder()
                        .date(dailyReportRequest.getDate())
                        .mood(dailyReportRequest.getMood())
                        .note(dailyReportRequest.getNote())
                        .patientDailyReport(patientService.getById(dailyReportRequest.getUserId()))
                        .build()
        );
    }
    public boolean checkIfDailyReportCanBeAdded(LocalDate localDate, long userId){
        return dailyReportRepository.countAllByDateAndPatientDailyReportId(localDate, userId) <1;
    }

    public List<Mood> getMoods(int numOfDays, long userId){
        List<DailyReport> reports = dailyReportRepository.getAllByPatientDailyReportId(userId);
        LocalDate dateDaysBefore = LocalDate.now().minusDays(numOfDays);
        List<Mood> moods = reports.stream().map((report -> new Mood(report.getMood(), report.getDate()))).toList();
        return moods.stream().filter((mood -> mood.getDate().isAfter(dateDaysBefore))).toList();
    }

    public List<Integer> getMoodsQuantity(long userId){
        List<DailyReport> reports = dailyReportRepository.getAllByPatientDailyReportId(userId);
        int terrible = (int) reports.stream().filter(report -> report.getMood().equals("terrible")).count();
        int bad =  (int) reports.stream().filter(report -> report.getMood().equals("bad")).count();
        int neutral = (int) reports.stream().filter(report -> report.getMood().equals("neutral")).count();
        int good = (int) reports.stream().filter(report -> report.getMood().equals("good")).count();
        int excellent = (int) reports.stream().filter(report -> report.getMood().equals("excellent")).count();
        return List.of(terrible, bad, neutral, good, excellent);
    }

    public DailyReport getById(long id){
        return this.dailyReportRepository.getById(id);
    }

    public void removeReport(long id){
        this.dailyReportRepository.delete(this.getById(id));
    }
}
