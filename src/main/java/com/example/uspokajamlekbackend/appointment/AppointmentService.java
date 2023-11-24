package com.example.uspokajamlekbackend.appointment;

import com.example.uspokajamlekbackend.user.doctor.DoctorService;
import com.example.uspokajamlekbackend.user.doctor.dto.DoctorResponse;
import com.example.uspokajamlekbackend.user.patient.PatientService;
import com.example.uspokajamlekbackend.user.patient.dto.PatientResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    public AppointmentResponse addAppointment(Long patientId, Long doctorId, LocalDateTime visitStartDate, LocalDateTime visitEndDate) {
        return createAppointmentResponse(this.appointmentRepository.save(Appointment.builder()
                .visitStartDate(visitStartDate)
                .visitEndDate(visitEndDate)
//                .patient(patientService.getById(patientId))
                .doctor(doctorService.getById(doctorId))
                .build()));
    }

    public List<AppointmentResponse> getFutureDoctorAppointment(Long doctorId) {
        return this.appointmentRepository.getAppointmentByDoctorId(doctorId).stream().filter(
                appointment -> appointment.getVisitStartDate().isAfter(LocalDateTime.now())
        ).map(appointment -> createAppointmentResponse(appointment)).toList();
    }

    public List<AppointmentResponse> getFuturePatientAppointment(Long patientId) {
        return this.appointmentRepository.getAppointmentByPatientId(patientId).stream().filter(
                appointment -> appointment.getVisitStartDate().isAfter(LocalDateTime.now())
        ).map(appointment -> createAppointmentResponse(appointment)).toList();
    }

    public List<AppointmentResponse> getPastPatientAppointments(Long patientId) {
        return this.appointmentRepository.getAppointmentByPatientId(patientId).stream().filter(
                appointment -> appointment.getVisitStartDate().isBefore(LocalDateTime.now())
        ).map(appointment -> createAppointmentResponse(appointment)).toList();
    }

    public List<AppointmentResponse> getPastDoctorAppointments(Long doctorId) {
        return this.appointmentRepository.getAppointmentByDoctorId(doctorId).stream().filter(
                appointment -> appointment.getVisitStartDate().isBefore(LocalDateTime.now())
        ).map(appointment -> createAppointmentResponse(appointment)).toList();
    }


    private AppointmentResponse createAppointmentResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .visitStartDate(appointment.getVisitStartDate())
                .visitEndDate(appointment.getVisitEndDate())
                .doctor(
                        DoctorResponse.createDoctorResponse(appointment.getDoctor())
                )
//                .patient(
//                        PatientResponse.createPatientResponse(appointment.getPatient())
//                )
                .build();
    }

    public boolean removeAppointment(Long appointmentId){
        Appointment appointment = appointmentRepository.getById(appointmentId);
        if (appointment != null){
            appointment.getDoctor().getAppointments().remove(appointment);
            appointment.getPatient().getAppointments().remove(appointment);
            appointmentRepository.deleteById(appointment.getId());
            return true;
        }
        return false;
    }

}
