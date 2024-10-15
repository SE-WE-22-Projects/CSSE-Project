package com.csse.healthSphere.controller;

import com.csse.healthSphere.dto.MedicalStaffRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.MedicalStaff;
import com.csse.healthSphere.service.MedicalStaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/staff")
@RestController
@RequiredArgsConstructor
public class MedicalStaffController {
    private final MedicalStaffService medicalStaffService;

    /**
     * Create a new medical staff
     *
     * @param medicalStaffRequest: the data for the new medical staff
     * @return the newly created medical staff
     */
    @PostMapping
    public ResponseEntity<MedicalStaff> createService(
            @RequestBody MedicalStaffRequest medicalStaffRequest
    ) {
        MedicalStaff createdMedicalStaff = medicalStaffService.createMedicalStaff(medicalStaffRequest);
        return new ResponseEntity<>(createdMedicalStaff, HttpStatus.CREATED);
    }

    /**
     * Get all medical staffs
     *
     * @return a list of all medical staffs
     */
    @GetMapping
    public ResponseEntity<List<MedicalStaff>> getAllServices() {
        List<MedicalStaff> medicalStaffList = medicalStaffService.getAllMedicalStaffs();
        return new ResponseEntity<>(medicalStaffList, HttpStatus.OK);
    }

    /**
     * Get a MedicalStaffService by id
     *
     * @param id the id of the medical staff
     * @return the medical staff for the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<MedicalStaff> getServiceById(
            @PathVariable Long id
    ) {
        MedicalStaff medicalStaff = medicalStaffService.getMedicalStaffById(id);
        return new ResponseEntity<>(medicalStaff, HttpStatus.OK);
    }

    /**
     * Update a medical staff by id
     *
     * @param id                  the id of the medical staff
     * @param medicalStaffRequest the updated content of the medical staff
     * @return the updated medical staff
     */
    @PutMapping("/{id}")
    public ResponseEntity<MedicalStaff> updateService(
            @PathVariable Long id,
            @RequestBody MedicalStaffRequest medicalStaffRequest
    ) {
        MedicalStaff updatedMedicalStaff = medicalStaffService.updateMedicalStaff(id, medicalStaffRequest);
        return new ResponseEntity<>(updatedMedicalStaff, HttpStatus.OK);
    }

    /**
     * Delete a medical staff by id
     *
     * @param id the id of the medical staff
     * @return an empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(
            @PathVariable Long id
    ) {
        medicalStaffService.deleteMedicalStaff(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
