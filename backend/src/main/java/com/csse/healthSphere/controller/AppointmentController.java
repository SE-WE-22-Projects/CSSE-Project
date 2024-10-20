package com.csse.healthSphere.controller;

import com.csse.healthSphere.dto.AppointmentCreation;
import com.csse.healthSphere.dto.AppointmentRequest;
import com.csse.healthSphere.dto.AuthUser;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Appointment;
import com.csse.healthSphere.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequestMapping("/api/appointment")
@RestController
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    /**
     * Create a new appointment
     * @param appointmentRequest: the data for the new appointment
     * @return the newly created appointment
     */
    @PostMapping
    public ResponseEntity<Appointment> createAppointment(
            @RequestBody AppointmentRequest appointmentRequest
    ) {
        Appointment createdAppointment = appointmentService.createAppointment(appointmentRequest);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    /**
     * Get all appointments
     * @return a list of all appointments
     */
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointmentList = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }

    /**
     * Get a appointment by id
     * @param id the id of the appointment
     * @return the appointment for the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(
            @PathVariable Long id
    ) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    /**
     * Update an appointment
     * @param id the id of the appointment
     * @param appointmentRequest the updated data for the appointment
     * @return the updated appointment
     */
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(
            @PathVariable Long id,
            @RequestBody AppointmentRequest appointmentRequest
    ) {
        Appointment updatedAppointment = appointmentService.updateAppointment(id, appointmentRequest);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }

    /**
     * Delete an appointment
     * @param id the id of the appointment
     * @return no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(
            @PathVariable Long id
    ) {
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Find appointments by patient
     * @param patientId the id of the patient
     * @return a list of all appointments for the given patient
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> findAppointmentsByPatient(
            @PathVariable Long patientId
    ) {
        List<Appointment> appointmentList = appointmentService.findAppointmentsByPatient(patientId);
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }

    /**
     * Find appointments by schedule
     * @param scheduleId the id of the schedule
     * @return a list of all appointments for the given schedule
     */
    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<Appointment>> findAppointmentsBySchedule(
            @PathVariable Long scheduleId
    ) {
        List<Appointment> appointmentList = appointmentService.findAppointmentsBySchedule(scheduleId);
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }

    /**
     * Find appointments by doctor
     * @param doctorId the id of the doctor
     * @return a list of all appointments for the given doctor
     */
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> findAppointsByDoctor(
            @PathVariable Long doctorId
    ){
        List<Appointment> appointmentList = appointmentService.findAppointmentsByDoctor(doctorId);
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }

    /**
     * Create a new appointment by patient
     * @param appointmentRequest: the data for the new appointment
     * @return the newly created appointment
     */
    @PostMapping("/patient")
    public ResponseEntity<Appointment> createAppointmentByPatient(
            @RequestBody AppointmentCreation appointmentRequest,
            Principal principal
    ) {
        var authUser = (AuthUser)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        Appointment createdAppointment = appointmentService.createAppointmentByPatient(appointmentRequest, authUser);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }
}
