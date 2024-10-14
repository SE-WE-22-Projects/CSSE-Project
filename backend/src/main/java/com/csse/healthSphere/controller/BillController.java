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

// FIXME: remove this

@RequestMapping("/api/bill")
@RestController
@RequiredArgsConstructor
public class BillController {
    private final AppointmentService appointmentService;

    // create new appointment
    @PostMapping
    public ResponseEntity<Appointment> createBill(
            @RequestBody AppointmentRequest appointmentRequest
    ) {
        Appointment createdAppointment = appointmentService.createAppointment(appointmentRequest);
        createdAppointment.setPatient(null);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);

    }

    // get all appointments
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllBills() {
        List<Appointment> appointmentList = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }

    // get appointment by id
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getBillById(
            @PathVariable Long id
    ) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    // update appointment by id
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateBill(
            @PathVariable Long id,
            @RequestBody AppointmentRequest appointmentRequest
    ) {
        Appointment updatedAppointment = appointmentService.updateAppointment(id, appointmentRequest);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }

    // delete appointment by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(
            @PathVariable Long id
    ) {
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // Exception handler for ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
