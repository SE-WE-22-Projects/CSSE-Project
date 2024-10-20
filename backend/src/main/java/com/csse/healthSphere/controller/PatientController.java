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

    /**
     * Create a new patient
     *
     * @param patientRequest: the data for the new patient
     * @return the newly created patient
     */
    @PostMapping
    public ResponseEntity<Patient> createPatient(
            @RequestBody PatientRequest patientRequest
    ) {
        Patient createdPatient = patientService.createPatient(patientRequest);
        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }

    /**
     * Get all patients
     *
     * @return a list of all patients
     */
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patientList = patientService.getAllPatients();
        return new ResponseEntity<>(patientList, HttpStatus.OK);
    }

    /**
     * Get a PatientService by id
     *
     * @param id the id of the patient
     * @return the patient for the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(
            @PathVariable Long id
    ) {
        Patient patient = patientService.getPatientById(id);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    /**
     * Update a patient
     *
     * @param id: the id of the patient to update
     * @param patientRequest: the new data for the patient
     * @return the updated patient
     */
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(
            @PathVariable Long id,
            @RequestBody PatientRequest patientRequest
    ) {
        Patient updatedPatient = patientService.updatePatient(id, patientRequest);
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }

    /**
     * Delete a patient
     *
     * @param id: the id of the patient to delete
     * @return no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(
            @PathVariable Long id
    ){
        patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
