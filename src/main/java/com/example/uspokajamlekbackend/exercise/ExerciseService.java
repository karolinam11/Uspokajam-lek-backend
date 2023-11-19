package com.example.uspokajamlekbackend.exercise;

import com.example.uspokajamlekbackend.doctor.DoctorService;
import com.example.uspokajamlekbackend.user.PatientService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DoctorService doctorService;

    public void addExercise(ExerciseRequest exerciseRequest) {
        Exercise exercise = Exercise.builder()
                .name(exerciseRequest.getName())
                .description(exerciseRequest.getDescription())
                .createdBy(doctorService.getById(exerciseRequest.getCreatedBy()))
                .category(exerciseRequest.getCategory())
                .duration(exerciseRequest.getDuration())
                .build();
        exerciseRepository.save(exercise);
    }

    public void updateExercise(ExerciseRequest exerciseRequest){
        Exercise exerciseDb = exerciseRepository.getById(exerciseRequest.getId());
        if(exerciseDb != null){
            Exercise exerciseToBeUpdated = modelMapper.map(exerciseRequest, Exercise.class);
            exerciseToBeUpdated.setId(exerciseDb.getId());
            exerciseRepository.save(exerciseToBeUpdated);
        }
    }

    public void deleteExercise(String name) {
        exerciseRepository.delete(exerciseRepository.getByName(name));
    }

    public List<ExerciseResponse> getAllExercises() {
        return exerciseRepository.findAll().stream().map(
                exercise -> {
                    return ExerciseResponse.createExerciseResponse(exercise);
                }
        ).toList();
    }


}
