package com.csse.healthSphere.controller;

import com.csse.healthSphere.dto.AdmissionRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Admission;
import com.csse.healthSphere.service.AdmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/admission")
@RestController
@RequiredArgsConstructor
public class AdmissionController {
    private final AdmissionService admissionService;

    /**
     * Create a new admission
     *
     * @param admissionRequest: the data for the new admission
     * @return the newly created admission
     */
    @PostMapping
    public ResponseEntity<Admission> createService(
            @RequestBody AdmissionRequest admissionRequest
    ) {
        Admission createdAdmission = admissionService.createAdmission(admissionRequest);
        return new ResponseEntity<>(createdAdmission, HttpStatus.CREATED);
    }

    /**
     * Get all admissions
     *
     * @return a list of all admissions
     */
    @GetMapping
    public ResponseEntity<List<Admission>> getAllServices() {
        List<Admission> admissionList = admissionService.getAllAdmissions();
        return new ResponseEntity<>(admissionList, HttpStatus.OK);
    }

    /**
     * Get a AdmissionService by id
     *
     * @param id the id of the admission
     * @return the admission for the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Admission> getServiceById(
            @PathVariable Long id
    ) {
        Admission admission = admissionService.getAdmissionById(id);
        return new ResponseEntity<>(admission, HttpStatus.OK);
    }

    /**
     * Update a admission by id
     *
     * @param id               the id of the admission
     * @param admissionRequest the updated content of the admission
     * @return the updated admission
     */
    @PutMapping("/{id}")
    public ResponseEntity<Admission> updateService(
            @PathVariable Long id,
            @RequestBody AdmissionRequest admissionRequest
    ) {
        Admission updatedAdmission = admissionService.updateAdmission(id, admissionRequest);
        return new ResponseEntity<>(updatedAdmission, HttpStatus.OK);
    }

    /**
     * Delete a admission by id
     *
     * @param id the id of the admission
     * @return an empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(
            @PathVariable Long id
    ) {
        admissionService.deleteAdmission(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<Admission> findAdmissionByAppointment(
            @PathVariable Long appointmentId
    ) {
        Admission admission = admissionService.findAdmissionByAppointment(appointmentId);
        return new ResponseEntity<>(admission, HttpStatus.OK);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Admission>> findAdmissionsByPatient(
            @PathVariable Long patientId
    ) {
        List<Admission> admissionList = admissionService.findAdmissionsByPatient(patientId);
        return new ResponseEntity<>(admissionList, HttpStatus.OK);
    }

    /**
     * Handle ResourceNotFoundException
     *
     * @param e the exception
     * @return a response containing an error message
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }


}
