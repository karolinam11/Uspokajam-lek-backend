package com.example.uspokajamlekbackend.user;

import com.example.uspokajamlekbackend.doctor.Doctor;
import com.example.uspokajamlekbackend.doctor.DoctorRepository;
import com.example.uspokajamlekbackend.doctor.dto.DoctorResponse;
import com.example.uspokajamlekbackend.user.dto.PatientResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

    public Role login(String email, String password) {

        if (patientRepository.existsByEmailAndPassword(email, password)) {
            return Role.PATIENT;
        } else if (doctorRepository.existsByEmailAndPassword(email, password)) {
            return Role.DOCTOR;
        }
        return null;
    }


    public Patient signup(String email, String password, String name, String surname, LocalDate birthDate, Role role) {
        if (!patientRepository.existsByEmail(email)) {
            return patientRepository.save(Patient.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .surname(surname)
                    .birthDate(birthDate)
                    .role(role)
                    .doctors(new ArrayList<>())
                    .requests(new ArrayList<>())
                    .build());
        } else {
            return null;
        }
    }

    public Patient getById(Long id) {
        return patientRepository.findById(id).get();
    }

    public PatientResponse getPatientByEmail(String email) {
        return PatientResponse.createPatientResponse(patientRepository.getByEmail(email));
    }

    public PatientResponse editAccount(String name, String surname, LocalDate birthDate, String email) {
        Patient patientToBeUpdated = patientRepository.getByEmail(email);
        patientToBeUpdated.setName(name);
        patientToBeUpdated.setSurname(surname);
        patientToBeUpdated.setBirthDate(birthDate);
        patientToBeUpdated.setEmail(email);
        Patient updatedPatient = patientRepository.save(patientToBeUpdated);
        return PatientResponse.createPatientResponse(updatedPatient);
    }

    public List<Doctor> getMyDoctors(Long patientId){
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
        }
        else{
            return false;
        }
    }

    public DoctorResponse getDoctorByEmail(String email) {
        Doctor doctor = doctorRepository.getByEmail(email);
        return DoctorResponse.createDoctorResponse(doctor);
    }

}
