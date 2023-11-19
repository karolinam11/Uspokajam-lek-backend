package com.example.uspokajamlekbackend.doctor;

import com.example.uspokajamlekbackend.doctor.dto.DoctorResponse;
import com.example.uspokajamlekbackend.user.dto.PatientResponse;
import com.example.uspokajamlekbackend.user.Role;
import com.example.uspokajamlekbackend.user.Patient;
import com.example.uspokajamlekbackend.user.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;

    public Doctor getById(Long doctorId){
        return doctorRepository.getById(doctorId);
    }

    public Doctor signup(String email, String password, String name, String surname, LocalDate birthDate, Role role, String specialization, String phoneNumber, String address) {
        if (!doctorRepository.existsByEmail(email)) {
            return doctorRepository.save(Doctor.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .surname(surname)
                    .birthDate(birthDate)
                    .role(role)
                    .address(address)
                    .phoneNumber(phoneNumber)
                    .specialization(specialization)
                    .patients(new ArrayList<>())
                    .invitationCode(generateInvitationCode())
                    .pendingRequests(List.of())
                    .build());
        } else {
            return null;
        }
    }

    public DoctorResponse editDoctorAccount(String name, String surname, LocalDate birthDate, String email, String specialization, String phoneNumber, String address) {
        Doctor doctorToBeUpdated = doctorRepository.getByEmail(email);
        doctorToBeUpdated.setName(name);
        doctorToBeUpdated.setSurname(surname);
        doctorToBeUpdated.setBirthDate(birthDate);
        doctorToBeUpdated.setEmail(email);
        doctorToBeUpdated.setSpecialization(specialization);
        doctorToBeUpdated.setPhoneNumber(phoneNumber);
        doctorToBeUpdated.setAddress(address);
        Doctor updatedDoctor = doctorRepository.save(doctorToBeUpdated);
        return DoctorResponse.createDoctorResponse(updatedDoctor);
    }

    public void assignPatientToDoctor(Long doctorId, Long patientId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + doctorId));
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patientId));
        if (!doctor.getPatients().contains(patient)) {
            patient.getDoctors().add(doctor);
            patientRepository.save(patient);
            doctor.getPatients().add(patient);
            doctorRepository.save(doctor);
        }
    }


    public List<PatientResponse> getPatients(Long doctorId) {
        return doctorRepository.getById(doctorId).getPatients().stream().map(
                patient -> {
                    return PatientResponse.builder()
                            .id(patient.getId())
                            .birthDate(patient.getBirthDate())
                            .email(patient.getEmail())
                            .name(patient.getName())
                            .surname(patient.getSurname())
                            .build();
                }
        ).toList();
    }

    public void acceptDoctorRequest(long patientId, long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + doctorId));
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patientId));
        if (!doctor.getPatients().contains(patient) && doctor.getPendingRequests().contains(patient)) {
            patient.getDoctors().add(doctor);
            patient.getRequests().remove(doctor);
            patientRepository.save(patient);
            doctor.getPatients().add(patient);
            doctor.getPendingRequests().remove(patient);
            doctorRepository.save(doctor);
        }
    }

    public void declineDoctorRequest(long patientId, long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + doctorId));
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patientId));
        if (!doctor.getPatients().contains(patient) && doctor.getPendingRequests().contains(patient)) {
            patient.getRequests().remove(doctor);
            patientRepository.save(patient);
            doctor.getPendingRequests().remove(patient);
            doctorRepository.save(doctor);
        }
    }

    public List<PatientResponse> getPendingRequests(long doctorId){
        return doctorRepository.getById(doctorId).getPendingRequests().stream().map(
                patient -> {
                    return PatientResponse.builder()
                            .id(patient.getId())
                            .birthDate(patient.getBirthDate())
                            .email(patient.getEmail())
                            .name(patient.getName())
                            .surname(patient.getSurname())
                            .build();
                }
        ).toList();
    }

    public Doctor getDoctorById(Long doctorId){
        return doctorRepository.getById(doctorId);
    }

    private String generateInvitationCode() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 15);
    }

}
