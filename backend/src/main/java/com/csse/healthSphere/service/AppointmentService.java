package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.AppointmentRequest;
import com.csse.healthSphere.enums.AppointmentStatus;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Appointment;
import com.csse.healthSphere.model.Doctor;
import com.csse.healthSphere.model.Patient;
import com.csse.healthSphere.model.Schedule;
import com.csse.healthSphere.repository.AppointmentRepository;
import com.csse.healthSphere.repository.DoctorRepository;
import com.csse.healthSphere.repository.PatientRepository;
import com.csse.healthSphere.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final ScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    /**
     * @param appointmentRequest
     * @return
     */
    public Appointment createAppointment(AppointmentRequest appointmentRequest) {
        Appointment appointment = modelMapper.map(appointmentRequest, Appointment.class);
        // fetch patient & check existence
        Patient patient = patientRepository.findById(appointmentRequest.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        Schedule schedule = scheduleRepository.findById(appointmentRequest.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));

        appointment.setPatient(patient);
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setSchedule(schedule);
        return appointmentRepository.save(appointment);
    }

    /**
     * @return
     */
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    /**
     * @param id
     * @return
     */
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
    }

    /**
     * @param id
     * @param appointmentRequest
     * @return
     */
    public Appointment updateAppointment(Long id, AppointmentRequest appointmentRequest) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        Schedule schedule = scheduleRepository.findById(appointmentRequest.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
        Patient patient = patientRepository.findById(appointmentRequest.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        modelMapper.map(appointmentRequest, appointment);
        appointment.setSchedule(schedule);
        appointment.setPatient(patient);

        return appointmentRepository.save(appointment);
    }

    /**
     * @param id
     */
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        appointmentRepository.delete(appointment);
    }

    /**
     * @param patientId
     * @return
     */
    public List<Appointment> findAppointmentsByPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        return appointmentRepository.findByPatient(patient);
    }

    /**
     * @param scheduleId
     * @return
     */
    public List<Appointment> findAppointmentsBySchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
        return appointmentRepository.findBySchedule(schedule);
    }

    /**
     * @param doctorId
     * @return
     */
    public List<Appointment> findAppointmentsByDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        return appointmentRepository.findByScheduleDoctor(doctor);
    }
}
