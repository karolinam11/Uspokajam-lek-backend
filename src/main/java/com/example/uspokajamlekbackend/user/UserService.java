package com.example.uspokajamlekbackend.user;

import com.example.uspokajamlekbackend.user.doctor.Doctor;
import com.example.uspokajamlekbackend.user.doctor.DoctorRepository;
import com.example.uspokajamlekbackend.user.patient.Patient;
import com.example.uspokajamlekbackend.user.patient.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public User login(String email, String password) {
        Optional<Patient> patient = patientRepository.findByEmailAndPassword(email,password);
        if(patient.isPresent()){
            return patient.get();
        }
        Optional<Doctor> doctor = doctorRepository.findByEmailAndPassword(email,password);
        if(doctor.isPresent()){
            return doctor.get();
        }
        throw new EntityNotFoundException();
    }
}
