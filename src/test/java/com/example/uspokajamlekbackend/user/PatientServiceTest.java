package com.example.uspokajamlekbackend.user;

import com.example.uspokajamlekbackend.user.doctor.Doctor;
import com.example.uspokajamlekbackend.user.doctor.DoctorRepository;
import com.example.uspokajamlekbackend.user.patient.Patient;
import com.example.uspokajamlekbackend.user.patient.PatientRepository;
import com.example.uspokajamlekbackend.user.patient.PatientService;
import com.example.uspokajamlekbackend.user.patient.Role;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSignupNewPatient() {
        Patient patient = new Patient();
        patient.setEmail("newpatient@example.com");
        patient.setName("John");
        patient.setSurname("Doe");

        when(patientRepository.existsByEmail(patient.getEmail())).thenReturn(false);
        when(patientRepository.save(patient)).thenReturn(patient);

        Patient savedPatient = patientService.signup(patient);

        assertEquals(patient.getEmail(), savedPatient.getEmail());
        assertEquals(patient.getName(), savedPatient.getName());
        assertEquals(patient.getSurname(), savedPatient.getSurname());
        assertEquals(Role.PATIENT, savedPatient.getRole());
        assertTrue(savedPatient.getDoctors().isEmpty());
    }

    @Test
    void shouldNotSignupExistingPatient() {
        Patient existingPatient = new Patient();
        existingPatient.setEmail("existingpatient@example.com");

        when(patientRepository.existsByEmail(existingPatient.getEmail())).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> patientService.signup(existingPatient));
    }

    @Test
    void shouldGetPatientById() {
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        Patient retrievedPatient = patientService.getById(patientId);

        assertEquals(patientId, retrievedPatient.getId());
    }

    @Test
    void shouldEditPatientAccount() {
        Patient existingPatient = new Patient();
        existingPatient.setEmail("existingpatient@example.com");
        existingPatient.setName("Alice");
        existingPatient.setSurname("Johnson");

        Patient updatedPatientDetails = new Patient();
        updatedPatientDetails.setEmail("updatedemail@example.com");
        updatedPatientDetails.setName("UpdatedName");
        updatedPatientDetails.setSurname("UpdatedSurname");

        when(patientRepository.findByEmail(updatedPatientDetails.getEmail())).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Patient editedPatient = patientService.editAccount(updatedPatientDetails);

        assertEquals(updatedPatientDetails.getEmail(), editedPatient.getEmail());
        assertEquals(updatedPatientDetails.getName(), editedPatient.getName());
        assertEquals(updatedPatientDetails.getSurname(), editedPatient.getSurname());
    }

    @Test
    void shouldGetMyDoctors() {
        Long patientId = 1L;
        Doctor doctor1 = new Doctor();
        doctor1.setId(101L);
        Doctor doctor2 = new Doctor();
        doctor2.setId(102L);

        Patient patient = new Patient();
        patient.setId(patientId);
        patient.setDoctors(Arrays.asList(doctor1, doctor2));

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        List<Doctor> myDoctors = patientService.getMyDoctors(patientId);

        assertEquals(2, myDoctors.size());
        assertTrue(myDoctors.contains(doctor1));
        assertTrue(myDoctors.contains(doctor2));
    }

    @Test
    void shouldCreateDoctorRequest() {
        Long patientId = 1L;
        String invitationCode = "ABC123";

        Doctor doctor = new Doctor();
        doctor.setId(101L);
        doctor.setInvitationCode(invitationCode);
        doctor.setPatients(new ArrayList<>());

        Patient patient = new Patient();
        patient.setId(patientId);

        when(doctorRepository.findByInvitationCode(invitationCode)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        assertTrue(patient.getDoctors().isEmpty());
        assertTrue(doctor.getPatients().isEmpty());

        boolean requestCreated = patientService.createDoctorRequest(patientId, invitationCode);

        assertTrue(requestCreated);
        assertTrue(doctor.getPendingRequests().contains(patient));
        verify(doctorRepository, times(1)).save(doctor);
    }

    // Test other methods similarly (editAccount, getMyDoctors, createDoctorRequest)
}

