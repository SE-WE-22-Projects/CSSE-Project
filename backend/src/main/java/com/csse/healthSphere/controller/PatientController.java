package com.csse.healthSphere.controller;


import com.csse.healthSphere.dto.PatientRequest;
import com.csse.healthSphere.model.Patient;
import com.csse.healthSphere.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/patient")
@RestController
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    // create new patient
    @PostMapping
    public ResponseEntity<Patient> createPatient(
            @RequestBody PatientRequest patientRequest
    ) {
        Patient createdPatient = patientService.createPatient(patientRequest);
        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }

    // get all patients
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patientList = patientService.getAllPatients();
        return new ResponseEntity<>(patientList, HttpStatus.OK);
    }

    // get patient by id
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(
            @PathVariable Long id
    ) {
        Patient patient = patientService.getPatientById(id);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }
}
