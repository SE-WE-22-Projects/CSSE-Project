package com.csse.healthSphere.controller;

import com.csse.healthSphere.dto.AppointmentRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Appointment;
import com.csse.healthSphere.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/appointment")
@RestController
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    // create new appointment
    @PostMapping
    public ResponseEntity<Appointment> createAppointment(
            @RequestBody AppointmentRequest appointmentRequest
    ) {
        Appointment createdAppointment = appointmentService.createAppointment(appointmentRequest);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    // get all appointments
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointmentList = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }

    // get appointment by id
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(
            @PathVariable Long id
    ) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    // update appointment by id
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(
            @PathVariable Long id,
            @RequestBody AppointmentRequest appointmentRequest
    ) {
        Appointment updatedAppointment = appointmentService.updateAppointment(id, appointmentRequest);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }

    // delete appointment by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(
            @PathVariable Long id
    ) {
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> findAppointmentsByPatient(
            @PathVariable Long patientId
    ) {
        List<Appointment> appointmentList = appointmentService.findAppointmentsByPatient(patientId);
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<Appointment>> findAppointmentsBySchedule(
            @PathVariable Long scheduleId
    ) {
        List<Appointment> appointmentList = appointmentService.findAppointmentsBySchedule(scheduleId);
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> findAppointsByDoctor(
            @PathVariable Long doctorId
    ){
        List<Appointment> appointmentList = appointmentService.findAppointmentsByDoctor(doctorId);
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }
}
