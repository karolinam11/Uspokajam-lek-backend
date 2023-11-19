package com.example.uspokajamlekbackend.user;

import com.example.uspokajamlekbackend.doctor.dto.AddPatientRequest;
import com.example.uspokajamlekbackend.user.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Role userRole = patientService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (userRole != null) {
            if (userRole.equals(Role.DOCTOR)) {
                return ResponseEntity.ok(patientService.getDoctorByEmail(loginRequest.getEmail()));
            } else if(userRole.equals(Role.PATIENT)) {
                return ResponseEntity.ok(patientService.getPatientByEmail(loginRequest.getEmail()));
            }
            else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        Patient registeredPatient = patientService.signup(signupRequest.getEmail(),
                signupRequest.getPassword(),
                signupRequest.getName(),
                signupRequest.getSurname(),
                signupRequest.getBirthDate(),
                Role.PATIENT);
        if (registeredPatient != null) {
            return ResponseEntity.ok(PatientResponse.createPatientResponse(registeredPatient));
        }
        return ResponseEntity.badRequest().body("Email already exists");
    }


    @PutMapping("edit-account")
    public ResponseEntity<?> editAccount(@RequestBody EditAccountRequest editAccountRequest) {
        return ResponseEntity.ok(this.patientService.editAccount(editAccountRequest.getName(), editAccountRequest.getSurname(), editAccountRequest.getBirthDate(), editAccountRequest.getEmail()));
    }

    @PostMapping("add-doctor-request")
    public ResponseEntity<?> addDoctor(@RequestBody AddDoctorRequest addDoctorRequest){
        if(patientService.createDoctorRequest(addDoctorRequest.getPatientId(),addDoctorRequest.getInvitationCode())){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("my-doctors/{id}")
    public ResponseEntity<?> getMyDoctors(@PathVariable Long id){
        return ResponseEntity.ok(this.patientService.getMyDoctors(id));
    }

}
