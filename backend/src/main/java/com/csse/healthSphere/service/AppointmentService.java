package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.AppointmentCreation;
import com.csse.healthSphere.dto.AppointmentRequest;
import com.csse.healthSphere.dto.AuthUser;
import com.csse.healthSphere.enums.AppointmentStatus;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.*;
import com.csse.healthSphere.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final ScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    private final ChargeRepository chargeRepository;

    /**
     * Create a new appointment
     * @param appointmentRequest
     * @return the newly created appointment
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
     * Get all appointments
     * @return a list of all appointments
     */
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    /**
     * Get a appointment by id
     * @param id
     * @return the appointment for the given id
     */
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
    }

    /**
     * Update an appointment
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
     * Delete an appointment
     * @param id
     * @return
     */
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        appointmentRepository.delete(appointment);
    }

    /**
     * Find appointments by patient
     * @param patientId
     * @return a list of all appointments for the given patient
     */
    public List<Appointment> findAppointmentsByPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        return appointmentRepository.findByPatient(patient);
    }

    /**
     * Find appointments by schedule
     * @param scheduleId
     * @return a list of all appointments for the given schedule
     */
    public List<Appointment> findAppointmentsBySchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
        return appointmentRepository.findBySchedule(schedule);
    }

    /**
     * Find appointments by doctor
     * @param doctorId
     * @return
     */
    public List<Appointment> findAppointmentsByDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        return appointmentRepository.findByScheduleDoctor(doctor);
    }

    /**
     *  Create a new appointment by patient
     * @param appointmentRequest
     * @param authUser
     * @return
     */
    public Appointment createAppointmentByPatient(AppointmentCreation appointmentRequest, AuthUser authUser) {
        Appointment appointment = modelMapper.map(appointmentRequest, Appointment.class);
        // fetch patient & check existence
        Patient patient = patientRepository.findById(authUser.getPerson().getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        Schedule schedule = scheduleRepository.findById(appointmentRequest.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));

        // get max queue number
        int queueNo = 1 + appointmentRepository.findMaxQueueNoByScheduleAndDate(appointmentRequest.getScheduleId(),appointmentRequest.getDate());
        // set time
        LocalTime time = schedule.getStartTime().plusMinutes((long) queueNo*15);
        appointment.setPatient(patient);
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setSchedule(schedule);
        appointment.setQueueNo(queueNo);
        appointment.setTime(time);
        Appointment createdAppointment =  appointmentRepository.save(appointment);

        //create charge
        Charge charge = new AppointmentCharge();
        charge.setAppointment(createdAppointment);
        charge.setAmount(2000.00F);
        chargeRepository.save(charge);

        //create charge
        Charge doctorCharge = new DoctorCharge();
        doctorCharge.setAppointment(createdAppointment);
        doctorCharge.setAmount(4000.00F);
        chargeRepository.save(doctorCharge);

        return createdAppointment;
    }
}