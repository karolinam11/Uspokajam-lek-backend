package com.example.uspokajamlekbackend.exercise;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@CrossOrigin(origins = "http://localhost:4200")
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @PostMapping("/exercises/add")
    public ResponseEntity<?> addExercise(@RequestBody ExerciseRequest exerciseRequest) {
        exerciseService.addExercise(exerciseRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/exercises")
    public ResponseEntity<?> update(@RequestBody ExerciseRequest exerciseRequest) {
        exerciseService.updateExercise(exerciseRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/exercises/delete")
    public ResponseEntity<?> deleteExercise(@RequestParam String name) {
        exerciseService.deleteExercise(name);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/exercises")
    public ResponseEntity<?> getExercise() {
        return ResponseEntity.ok(exerciseService.getAllExercises());
    }




}
