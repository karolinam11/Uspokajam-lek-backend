package com.example.uspokajamlekbackend.doctor;

import com.example.uspokajamlekbackend.doctor.dto.AddPatientRequest;
import com.example.uspokajamlekbackend.doctor.dto.DoctorResponse;
import com.example.uspokajamlekbackend.doctor.dto.EditDoctorAccountRequest;
import com.example.uspokajamlekbackend.user.*;
import com.example.uspokajamlekbackend.doctor.dto.DoctorSignupRequest;
import com.example.uspokajamlekbackend.user.dto.PatientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/doctor/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.doctorService.getDoctorById(id));
    }

    @PostMapping("/signup-doctor")
    public ResponseEntity<?> signup(@RequestBody DoctorSignupRequest signupRequest) {
        Doctor registeredUser = doctorService.signup(signupRequest.getEmail(),
                signupRequest.getPassword(),
                signupRequest.getName(),
                signupRequest.getSurname(),
                signupRequest.getBirthDate(),
                Role.DOCTOR,
                signupRequest.getSpecialization(),
                signupRequest.getPhoneNumber(),
                signupRequest.getAddress());
        if (registeredUser != null) {
            return ResponseEntity.ok(DoctorResponse.createDoctorResponse(registeredUser));
        }
        return ResponseEntity.badRequest().body("Email already exists");
    }

    @PutMapping("edit-doctor-account")
    public ResponseEntity<?> editDoctorAccount(@RequestBody EditDoctorAccountRequest editAccountRequest) {
        return ResponseEntity.ok(this.doctorService.editDoctorAccount(editAccountRequest.getName(), editAccountRequest.getSurname(), editAccountRequest.getBirthDate(), editAccountRequest.getEmail(), editAccountRequest.getSpecialization(), editAccountRequest.getPhoneNumber(), editAccountRequest.getAddress()));
    }

    @PostMapping("add-patient")
    public ResponseEntity<?> addPatient(@RequestBody AddPatientRequest addPatientRequest) {
        this.doctorService.assignPatientToDoctor(addPatientRequest.getDoctorId(), addPatientRequest.getPatientId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("patients")
    public ResponseEntity<?> getPatients(@RequestParam long id) {
        return ResponseEntity.ok(this.doctorService.getPatients(id));
    }

    @GetMapping("pending-requests")
    public ResponseEntity<?> getPendingRequests(@RequestParam long id){
        return ResponseEntity.ok(doctorService.getPendingRequests(id));
    }

    @PostMapping("accept-pending-request")
    public ResponseEntity<?> acceptPendingRequest(@RequestBody AddPatientRequest addPatientRequest){
        doctorService.acceptDoctorRequest(addPatientRequest.getPatientId(),addPatientRequest.getDoctorId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("decline-pending-request")
    public ResponseEntity<?> declinePendingRequest(@RequestBody AddPatientRequest addPatientRequest){
        doctorService.declineDoctorRequest(addPatientRequest.getPatientId(),addPatientRequest.getDoctorId());
        return ResponseEntity.ok().build();
    }

}
