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


@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    // create new appointment
    @PostMapping
    public ResponseEntity<Appointment> createAppointment(
            @RequestBody AppointmentRequest appointmentRequest
            ){
        Appointment createdAppointment = appointmentService.createAppointment(appointmentRequest);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);

    }

    // get all appointments
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments(){
        List<Appointment> appointmentList = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }

    // get appointment by id
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(
            @PathVariable Long id
    ){
        Appointment appointment = appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointment,HttpStatus.OK);
    }

    // update appintment by id
    @PutMapping("/{id}")
    public  ResponseEntity<Appointment> updateAppointment(
            @PathVariable Long id,
            @RequestBody AppointmentRequest appointmentRequest
    ){
        Appointment updatedAppointment = appointmentService.updateAppointment(id,appointmentRequest);
        return new ResponseEntity<>(updatedAppointment,HttpStatus.OK);
    }

    // delete appointment by id
    @DeleteMapping("/{id")
    public ResponseEntity<Void> deleteAppointment(
            @PathVariable Long id
    ){
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // Exception handler for ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
