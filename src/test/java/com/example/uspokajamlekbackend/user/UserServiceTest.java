package com.example.uspokajamlekbackend.user;

import com.example.uspokajamlekbackend.user.doctor.Doctor;
import com.example.uspokajamlekbackend.user.doctor.DoctorRepository;
import com.example.uspokajamlekbackend.user.patient.Patient;
import com.example.uspokajamlekbackend.user.patient.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.print.Doc;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginPatient() {
        String email = "patient@example.com";
        String password = "password";

        Patient patient = new Patient();
        patient.setId(1L);

        when(patientRepository.findByEmailAndPassword(email, password))
                .thenReturn(Optional.of(patient));

        Patient patientDb = (Patient) userService.login(email, password);

        assertEquals(patient.getId(), patientDb.getId());

    }


    @Test
    void testLoginDoctor() {
        String email = "doctor@example.com";
        String password = "password";

        Doctor doctor = new Doctor();
        doctor.setId(1L);

        when(doctorRepository.findByEmailAndPassword(email, password))
                .thenReturn(Optional.of(doctor));

        Doctor doctorDb = (Doctor) userService.login(email, password);

        assertEquals(doctor.getId(), doctorDb.getId());
    }

    @Test
    void shouldThrowEntityNotFoundWhenUserDoesntExist() {
        String email = "unknown@example.com";
        String password = "password";

        when(patientRepository.findByEmailAndPassword(email, password))
                .thenReturn(Optional.empty());
        when(doctorRepository.findByEmailAndPassword(email, password))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.login(email, password));
    }
}
