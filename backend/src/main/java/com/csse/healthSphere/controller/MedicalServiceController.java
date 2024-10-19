package com.csse.healthSphere.controller;

import com.csse.healthSphere.dto.MedicalServiceRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.MedicalService;
import com.csse.healthSphere.service.MedicalServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/service")
@RestController
@RequiredArgsConstructor
public class MedicalServiceController {
    private final MedicalServiceService medicalServiceService;

    /**
     * Create a new medical service
     *
     * @param medicalServiceRequest: the data for the new medical service
     * @return the newly created service
     */
    @PostMapping
    public ResponseEntity<MedicalService> createService(
            @RequestBody MedicalServiceRequest medicalServiceRequest
    ) {
        MedicalService createdMedicalService = medicalServiceService.createService(medicalServiceRequest);
        return new ResponseEntity<>(createdMedicalService, HttpStatus.CREATED);
    }

    /**
     * Get all medical services
     *
     * @return a list of all medical services
     */
    @GetMapping
    public ResponseEntity<List<MedicalService>> getAllServices() {
        List<MedicalService> medicalServiceList = medicalServiceService.getAllServices();
        return new ResponseEntity<>(medicalServiceList, HttpStatus.OK);
    }

    /**
     * Get a medical service by id
     *
     * @param id the id of the medical service
     * @return the medical service for the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<MedicalService> getServiceById(
            @PathVariable Long id
    ) {
        MedicalService medicalService = medicalServiceService.getServiceById(id);
        return new ResponseEntity<>(medicalService, HttpStatus.OK);
    }

    /**
     * Update a medical service by id
     *
     * @param id                    the id of the medical service
     * @param medicalServiceRequest the updated content of the medical service
     * @return the updated medical service
     */
    @PutMapping("/{id}")
    public ResponseEntity<MedicalService> updateService(
            @PathVariable Long id,
            @RequestBody MedicalServiceRequest medicalServiceRequest
    ) {
        MedicalService updatedMedicalService = medicalServiceService.updateService(id, medicalServiceRequest);
        return new ResponseEntity<>(updatedMedicalService, HttpStatus.OK);
    }

    /**
     * Delete a medical service by id
     *
     * @param id the id of the medical service
     * @return an empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(
            @PathVariable Long id
    ) {
        medicalServiceService.deleteService(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
