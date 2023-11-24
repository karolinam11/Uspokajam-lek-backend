package com.example.uspokajamlekbackend.user.patient;

import com.example.uspokajamlekbackend.user.User;
import com.example.uspokajamlekbackend.user.doctor.Doctor;
import com.example.uspokajamlekbackend.user.doctor.DoctorRepository;
import com.example.uspokajamlekbackend.user.doctor.dto.DoctorResponse;
import com.example.uspokajamlekbackend.user.patient.dto.PatientResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    ModelMapper modelMapper;

    public Patient signup(Patient patient) {
        patient.setRole(Role.PATIENT);
        if (patientRepository.existsByEmail(patient.getEmail())) {
            throw new EntityExistsException();
        } else {
            patient.setDoctors(Collections.emptyList());
            return patientRepository.save(patient);
        }
    }

    public Patient getById(Long id) {
        return patientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Patient editAccount(Patient patient) {
        Patient patientDb = patientRepository.findByEmail(patient.getEmail()).orElseThrow(EntityNotFoundException::new);
        patientDb.setName(patient.getName());
        patientDb.setSurname(patient.getSurname());
        patientDb.setBirthDate(patient.getBirthDate());
        patientDb.setEmail(patient.getEmail());
        return patientRepository.save(patientDb);
    }

    public List<Doctor> getMyDoctors(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patientId));
        return patient.getDoctors();
    }

    public boolean createDoctorRequest(long patientId, String invitationCode) {
        Doctor doctor = doctorRepository.findByInvitationCode(invitationCode)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with invitationCode: " + invitationCode));
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patientId));
        if (!doctor.getPatients().contains(patient)) {
            patient.getRequests().add(doctor);
            patientRepository.save(patient);
            doctor.getPendingRequests().add(patient);
            doctorRepository.save(doctor);
            return true;
        } else {
            return false;
        }
    }

}
