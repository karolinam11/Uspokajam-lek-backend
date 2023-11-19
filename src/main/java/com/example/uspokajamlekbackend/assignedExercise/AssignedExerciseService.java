package com.example.uspokajamlekbackend.assignedExercise;

import com.example.uspokajamlekbackend.appointment.Appointment;
import com.example.uspokajamlekbackend.doctor.DoctorRepository;
import com.example.uspokajamlekbackend.doctor.DoctorService;
import com.example.uspokajamlekbackend.doctor.dto.DoctorResponse;
import com.example.uspokajamlekbackend.exercise.Exercise;
import com.example.uspokajamlekbackend.exercise.ExerciseRepository;
import com.example.uspokajamlekbackend.exercise.ExerciseResponse;
import com.example.uspokajamlekbackend.user.PatientRepository;
import com.example.uspokajamlekbackend.user.PatientService;
import com.example.uspokajamlekbackend.user.dto.PatientResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignedExerciseService {

    @Autowired
    private AssignedExerciseRepository assignedExerciseRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;


    public void assignExercise(Long doctorId, Long patientId, Long exerciseId, LocalDate dueDate){
        this.assignedExerciseRepository.save(
                AssignedExercise.builder()
                        .exercise(exerciseRepository.getById(exerciseId))
                        .assignedBy(doctorRepository.getById(doctorId))
                        .assignedTo(patientRepository.getById(patientId))
                        .status(Status.NEW)
                        .dueDate(dueDate)
                        .isDone(false)
                        .build()
        );
    }

    public List<AssignedExerciseResponse> getUserAssignedExercises(Long patientId){
        return this.assignedExerciseRepository.getAssignedExerciseByAssignedToId(patientId)
                .stream().map(exercise -> {
                    return AssignedExerciseResponse.builder()
                            .id(exercise.getId())
                            .assignedBy(DoctorResponse.createDoctorResponse(exercise.getAssignedBy()))
                            .assignedTo(PatientResponse.createPatientResponse(exercise.getAssignedTo()))
                            .dueDate(exercise.getDueDate())
                            .exercise(ExerciseResponse.createExerciseResponse(exercise.getExercise()))
                            .isDone(exercise.isDone())
                            .status(exercise.getStatus())
                            .build();
                }).toList();
    }

    public List<AssignedExerciseResponse> getNewUserAssignedExercises(Long patientId){
        List<AssignedExercise> newExercises = this.assignedExerciseRepository.getAssignedExerciseByAssignedToIdAndStatus(patientId, Status.NEW);

        newExercises = newExercises.stream().map(exercise -> setExerciseAsSeen(exercise)).toList();
        return newExercises.stream().map(exercise -> {
                    return AssignedExerciseResponse.builder()
                            .id(exercise.getId())
                            .assignedBy(DoctorResponse.createDoctorResponse(exercise.getAssignedBy()))
                            .assignedTo(PatientResponse.createPatientResponse(exercise.getAssignedTo()))
                            .dueDate(exercise.getDueDate())
                            .exercise(ExerciseResponse.createExerciseResponse(exercise.getExercise()))
                            .isDone(exercise.isDone())
                            .status(exercise.getStatus())
                            .build();
                }).toList();
    }

    public List<AssignedExerciseResponse> getUserAssignedExercisesToday(Long patientId){
        return this.assignedExerciseRepository.getAssignedExerciseByAssignedToId(patientId)
                .stream()
                .filter(exercise -> {
                    return exercise.getDueDate().equals(LocalDate.now());
                })
                .map(exercise -> {
                    return AssignedExerciseResponse.builder()
                            .id(exercise.getId())
                            .assignedBy(DoctorResponse.createDoctorResponse(exercise.getAssignedBy()))
                            .assignedTo(PatientResponse.createPatientResponse(exercise.getAssignedTo()))
                            .dueDate(exercise.getDueDate())
                            .exercise(ExerciseResponse.createExerciseResponse(exercise.getExercise()))
                            .isDone(exercise.isDone())
                            .build();
                }).toList();
    }

    public boolean removeAssignedExercise(Long exerciseId){
        AssignedExercise exercise = assignedExerciseRepository.getById(exerciseId);
        if (exercise != null){
            exercise.getAssignedTo().getAssignedExercises().remove(exercise);
            exercise.getAssignedBy().getExercisesAssignedByUser().remove(exercise);
            assignedExerciseRepository.delete(exercise);
            return true;
        }
        return false;
    }

    public boolean setExerciseStatus(Long exerciseId){
        AssignedExercise exercise = assignedExerciseRepository.getById(exerciseId);
        if (exercise != null){
            exercise.setDone(true);
            assignedExerciseRepository.save(exercise);
            return true;
        }
        return false;
    }

    public AssignedExercise setExerciseAsSeen(AssignedExercise exercise){
            exercise.setStatus(Status.SEEN);
            return assignedExerciseRepository.save(exercise);
    }

    public List<AssignedExerciseResponse> getExercisesDoneLastWeek(long patientId){
        return this.assignedExerciseRepository.getAssignedExerciseByAssignedToId(patientId)
                .stream()
                .filter(exercise -> {
                    return exercise.getDueDate().isBefore(LocalDate.now())
                            && exercise.getDueDate().isAfter(LocalDate.now().minusDays(7))
                            && exercise.isDone();
                })
                .map(exercise -> {
                    return AssignedExerciseResponse.builder()
                            .id(exercise.getId())
                            .assignedBy(DoctorResponse.createDoctorResponse(exercise.getAssignedBy()))
                            .assignedTo(PatientResponse.createPatientResponse(exercise.getAssignedTo()))
                            .dueDate(exercise.getDueDate())
                            .exercise(ExerciseResponse.createExerciseResponse(exercise.getExercise()))
                            .build();
                }).toList();
    }


}
