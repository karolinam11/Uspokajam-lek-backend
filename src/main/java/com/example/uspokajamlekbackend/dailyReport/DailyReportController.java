package com.example.uspokajamlekbackend.dailyReport;

import com.example.uspokajamlekbackend.dailyReport.dto.DailyReportRequest;
import com.example.uspokajamlekbackend.dailyReport.dto.MoodsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class DailyReportController {

    @Autowired
    private DailyReportService dailyReportService;

    @GetMapping("/daily-reports")
    public ResponseEntity<List<DailyReport>> getUserDailyReports(@RequestParam Long id) {
        return ResponseEntity.ok(dailyReportService.getUserDailyReports(id));
    }

    @GetMapping("daily-reports-today")
    public ResponseEntity<Boolean> checkIfDailyReportAddedToday(@RequestParam Long id) {
        return ResponseEntity.ok(dailyReportService.checkIfDailyReportCanBeAdded(LocalDate.now(), id));
    }

    @PostMapping("/daily-reports/add")
    public ResponseEntity<?> addActivity(@RequestBody DailyReportRequest dailyReportRequest) {
        if (this.dailyReportService.checkIfDailyReportCanBeAdded(dailyReportRequest.getDate(), dailyReportRequest.getUserId())) {
            this.dailyReportService.addDailyReport(dailyReportRequest);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("daily-reports/moods")
    public ResponseEntity<?> getMoods(@RequestBody MoodsRequest moodsRequest) {
        return ResponseEntity.ok(this.dailyReportService.getMoods(moodsRequest.getNumOfDays(), moodsRequest.getUserId()));
    }

    @GetMapping("daily-reports/moods-quantity")
    public ResponseEntity<?> getMoodsQuantity(@RequestParam Long id) {
        return ResponseEntity.ok(this.dailyReportService.getMoodsQuantity(id));
    }

    @DeleteMapping("daily-reports/delete")
    public ResponseEntity<?> deleteReport(@RequestParam Long id) {
        this.dailyReportService.removeReport(id);
        return ResponseEntity.ok().build();
    }


}
