package com.example.uspokajamlekbackend.config;

import com.example.uspokajamlekbackend.activity.Activity;
import com.example.uspokajamlekbackend.activity.ActivityRepository;
import com.example.uspokajamlekbackend.appointment.Appointment;
import com.example.uspokajamlekbackend.appointment.AppointmentRepository;
import com.example.uspokajamlekbackend.assignedExercise.AssignedExercise;
import com.example.uspokajamlekbackend.assignedExercise.AssignedExerciseRepository;
import com.example.uspokajamlekbackend.assignedExercise.Status;
import com.example.uspokajamlekbackend.dailyReport.DailyReport;
import com.example.uspokajamlekbackend.dailyReport.DailyReportRepository;
import com.example.uspokajamlekbackend.exercise.Exercise;
import com.example.uspokajamlekbackend.exercise.ExerciseRepository;
import com.example.uspokajamlekbackend.user.doctor.Doctor;
import com.example.uspokajamlekbackend.user.doctor.DoctorRepository;
import com.example.uspokajamlekbackend.user.patient.Patient;
import com.example.uspokajamlekbackend.user.patient.PatientRepository;
import com.example.uspokajamlekbackend.user.patient.Role;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AssignedExerciseRepository assignedExerciseRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DailyReportRepository dailyReportRepository;

    @Autowired
    private ActivityRepository activityRepository;


    @PostConstruct
    private void addData() {
        Doctor doctor = Doctor.builder()
                .id(1L)
                .email("specjalista@email.pl")
                .password("password")
                .address("ul. Wielka 15 Wrocław")
                .role(Role.DOCTOR)
                .name("Anna")
                .surname("Nowak")
                .specialization("Psycholog")
                .appointments(List.of())
                .phoneNumber("112113114")
                .birthDate(LocalDate.of(1991, 5, 10))
                .invitationCode("9dv423hfe")
                .build();

        Patient patient = Patient.builder()
                .role(Role.PATIENT)
                .name("Jan")
                .surname("Kowalski")
                .email("pacjent@email.pl")
                .birthDate(LocalDate.of(1998, 06, 29))
                .doctors(List.of(doctor))
                .password("password")
                .build();

        Exercise exercise1 = new Exercise(1L, "Okaż sobie życzliwość", "1.Pomyśl o tym co sobie mówisz, kiedy z powodu lęku czy przygnębienia trudno Ci wykonać jakieś zadanie. Pamietaj, że jeśli zdarza Ci się myśleć niemiłe rzeczy o sobie to nie jesteś w tym odosobniony. Często jesteśmy swoimi najsurowszymi krytykami.\n 2.Zastanów się, co powiedziałbyś w podobnej sytuacji swojemu przyjacielowi czy przyjaciółce. \n 3.Obdarz tymi życzliwymi słowami samego siebie. \n 4.Pomyśl co miłego możesz dla siebie zrobić \n 5.Odpuść sobie. Pomyśl czy możesz podejśc do swoich przyszłych porażek w bardziej wyrozumiały sposób.", "short", "Inne", null);
        Exercise exercise2 = new Exercise(2L, "Samoukojenie", "Zdolność samoukojnenia przydaje się kiedy czujesz się przytłocozny emocjami. W tym ćwiczeniu wybierz aktywność dla każdego z twoich pięciu zmysłów żeby je uspokoić i wykonaj je. Wybierz coś co lubisz robić. \n Przykłady: \n 1. Wzrok: idź na spacer i zwróć uwagę na otaczającą Cię przyrodę. \n 2. Słuch: posłuchaj uspokajającej piosenki, którą lubisz. \n 3. Dotyk: weź kąpiel i zrelaksuj się w wodzie. \n 4. Smak: zrób sobie przyjemność i zjedz powoli coś wyjątkowo pysznego. \n 5. Węch: Powąchaj swoje ulubione perfumy lub kwiaty.", "long", "Inne", null);
        Exercise exercise3 = new Exercise(3L, "Oddychanie w kwadrat", "Użyj tej techniki kiedy potrzebujesz się uspokoić. \n 1.Znajdź wygodna pozycję. \n 2.Przez 4 sekundy rób powolny wdech. \n 3.Wstrzymaj oddech przez 4 sekundy. \n 4.Powoli wydychaj powietrze przez 4 sekundy. \n 5.Wstrzymaj oddech przez 4 sekundy. \n 6.Powtórz wszystkie czynności 4 razy lub tyle ile potrzebujesz.", "short", "Oddechowe", null);
        Exercise exercise4 = new Exercise(5L, "Oddychanie przeponowe", "Wykonaj to ćwiczenie w chwili stresu. \n 1.Znajdź wygodną pozycję. Zakmnij oczy lub skup się na jakiś przedmiocie leżącym przed tobą. \n 2.Połóż jedną rękę na piersi, drugą na brzuchu. Sprawdź która ręka porusza się kiedy oddychasz normalnie. \n 3. Zrób powolny wdech nosem. Wdychając powietrze wyobraź sobie, jak twój brzuch wypełnia się nim jak balon. Poczuj jak naciska na twoją dłoń. \n 4. Zrób powolny wydech ustami. Wydychając powietrze wyobraź sobie, jak balon się opróźnia. Poczuj jak twój brzuch opada. \n 5. Powtórz ile razy potrzebuejsz.", "medium", "Oddechowe", null);

        if (exerciseRepository.findAll().size() == 0) {
            List<Exercise> exerciseList = List.of(
                    exercise1,
                    exercise2,
                    exercise3,
                    exercise4,
                    new Exercise(4L, "Czas na zmartwienia", "Kiedy czujesz że przytłaczają Cię zmartwienia użyj tej techniki. Strategia ta skupia się na przesuwaniu zamartwiania się na później poprzez ustanowienie czasu, w którym możesz się martwić dowoli. \n 1.Wybierz 10-minutowe okno, które będzie twoim \" czasem na zmartiwenia\". \n 2.Kiedy w ciągu dnia w twoim umyśle pojawi się jakieś zmartwienie, szybko zanotuj sprawę na później. Możesz to zrobić w notatce codziennego raportu. Jako, że nie jest to twój czas na zmartwienia, z powrotem przenieś swoją uwage na to co robisz. \n 3.Kiedy przyjdzie twój \"czas na zmartwienia\" wyciągnij listę i do dzieła! Zastanów się czy zmartwienie męczy Cię z tą samą siłą co wcześniej. \n 4.Jeśli przyłapiesz się na rozmyślaniu o tych samych zmartwieniach po wyznaczonym czasie, zapisz je i wróć do nich w odpowiednim czasie następnego dnia.", "medium", "Inne", null),
                    new Exercise(6L, "Szybki spacer lub jogging", "Pójdź do ulubionego parku na spacer lub jogging. Staraj się skupić na swoim oddechu i otoczeniu. Możesz posłuchac ulubionej muzyki, aby bardziej się zrelaksować.", "long", "Fizyczne", null),
                    new Exercise(7L, "Rozciąganie całego ciała", "Wykonaj ulubione ćwiczenia, które pomoga rozluźnić spięte mięśnie. Skup się na odczuciach w ciele i oddechu, w celu pogłębienia rozciągania.", "medium", "Fizyczne", null)
            );
            exerciseRepository.saveAll(exerciseList);
        }

        if (doctorRepository.findAll().size() == 0) {
            doctorRepository.save(doctor);
        }

        if (patientRepository.findAll().size() == 0) {
            patientRepository.save(patient);
        }

        if (assignedExerciseRepository.findAll().size() == 0) {
            AssignedExercise assignedExercise = AssignedExercise.builder()
                    .assignedBy(doctor)
                    .exercise(exercise4)
                    .assignedTo(patient)
                    .status(Status.SEEN)
                    .isDone(true)
                    .build();
            for (int i = 1; i < 30; i++) {
                assignedExercise.setId((long) i);
                assignedExercise.setDueDate(LocalDate.now().minusDays(6 - i));
                assignedExercise.setDone(assignedExercise.getDueDate().isBefore(LocalDate.now()));
                assignedExerciseRepository.save(assignedExercise);
            }
            assignedExercise.setExercise(exercise1);
            assignedExerciseRepository.save(assignedExercise);
        }

        if (appointmentRepository.findAll().size() == 0) {
            Appointment appointment = Appointment.builder()
                    .patient(patient)
                    .doctor(doctor)
                    .visitStartDate(LocalDateTime.now())
                    .visitEndDate(LocalDateTime.now())
                    .build();

            for (int i = 1; i < 7; i++) {
                appointment.setId((long) i);
                appointment.setVisitStartDate(LocalDateTime.now().minusDays((5 - i) * 7).withHour(15).withMinute(0).withSecond(0));
                appointment.setVisitEndDate(LocalDateTime.now().minusDays((5 - i) * 7).withHour(16).withMinute(0).withSecond(0));
                appointmentRepository.save(appointment);
            }
        }

        if (dailyReportRepository.findAll().size() == 0) {
            dailyReportRepository.saveAll(
                    List.of(
                            new DailyReport(1L, LocalDate.now().minusDays(1), "terrible", "Miałem ciężki dzień", patient),
                            new DailyReport(2L, LocalDate.now().minusDays(2), "excellent", "Miałem świetny dzień", patient),
                            new DailyReport(3L, LocalDate.now().minusDays(3), "good", "Rozmyślałem nad sensem życia", patient),
                            new DailyReport(4L, LocalDate.now().minusDays(4), "terrible", "Miałem ciężki dzień", patient),
                            new DailyReport(5L, LocalDate.now().minusDays(5), "bad", "Miałem ciężki dzień", patient),
                            new DailyReport(6L, LocalDate.now().minusDays(6), "terrible", "Miałem ciężki dzień", patient),
                            new DailyReport(7L, LocalDate.now().minusDays(7), "terrible", "Miałem ciężki dzień", patient),
                            new DailyReport(8L, LocalDate.now().minusDays(8), "excellent", "Miałem świetny dzień", patient),
                            new DailyReport(9L, LocalDate.now().minusDays(9), "good", "Rozmyślałem nad sensem życia", patient),
                            new DailyReport(10L, LocalDate.now().minusDays(10), "terrible", "Miałem ciężki dzień", patient),
                            new DailyReport(11L, LocalDate.now().minusDays(11), "bad", "Miałem ciężki dzień", patient),
                            new DailyReport(12L, LocalDate.now().minusDays(12), "terrible", "Miałem ciężki dzień", patient),
                            new DailyReport(13L, LocalDate.now().minusDays(13), "terrible", "Miałem ciężki dzień", patient),
                            new DailyReport(14L, LocalDate.now().minusDays(14), "excellent", "Miałem świetny dzień", patient),
                            new DailyReport(15L, LocalDate.now().minusDays(15), "good", "Rozmyślałem nad sensem życia", patient),
                            new DailyReport(16L, LocalDate.now().minusDays(16), "terrible", "Miałem ciężki dzień", patient),
                            new DailyReport(17L, LocalDate.now().minusDays(17), "bad", "Miałem ciężki dzień", patient),
                            new DailyReport(18L, LocalDate.now().minusDays(18), "terrible", "Miałem ciężki dzień", patient)
                    )
            );
        }

        if (activityRepository.findAll().size() == 0) {
            activityRepository.saveAll(List.of(
                    new Activity(1L, "spacer", LocalDate.now().minusDays(1), "excellent", patient),
                    new Activity(2L, "spacer", LocalDate.now().minusDays(2), "terrible", patient),
                    new Activity(3L, "spacer", LocalDate.now().minusDays(3), "bad", patient),
                    new Activity(4L, "spacer", LocalDate.now().minusDays(4), "bad", patient),
                    new Activity(5L, "spacer", LocalDate.now().minusDays(5), "terrible", patient),
                    new Activity(6L, "spacer", LocalDate.now().minusDays(20), "excellent", patient)

            ));
        }
    }
}
