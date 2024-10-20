package com.csse.healthSphere.controller;

import com.csse.healthSphere.dto.DoctorRequest;
import com.csse.healthSphere.dto.WardAllocation;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Doctor;
import com.csse.healthSphere.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/doctor")
@RestController
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    /**
     * Create a new doctor
     *
     * @param doctorRequest: the data for the new doctor
     * @return the newly created doctor
     */
    @PostMapping
    public ResponseEntity<Doctor> createDoctor(
            @RequestBody DoctorRequest doctorRequest
    ) {
        Doctor createdDoctor = doctorService.createDoctor(doctorRequest);
        return new ResponseEntity<>(createdDoctor, HttpStatus.CREATED);
    }

    /**
     * Get all doctors
     *
     * @return a list of all doctors
     */
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctorList = doctorService.getAllDoctors();
        return new ResponseEntity<>(doctorList, HttpStatus.OK);
    }

    /**
     * Get a DoctorService by id
     *
     * @param id the id of the doctor
     * @return the doctor for the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(
            @PathVariable Long id
    ) {
        Doctor doctor = doctorService.getDoctorById(id);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    /**
     * Update a doctor by id
     *
     * @param id: the id of the doctor
     * @param doctorRequest: the updated data for the doctor
     * @return the updated doctor
     */
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(
            @PathVariable Long id,
            @RequestBody DoctorRequest doctorRequest
    ) {
        Doctor updatedDoctor = doctorService.updateDoctor(id, doctorRequest);
        return new ResponseEntity<>(updatedDoctor, HttpStatus.OK);
    }

    /**
     * Delete a doctor by id
     *
     * @param id: the id of the doctor
     * @return a response entity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(
            @PathVariable Long id
    ) {
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Find doctors by department
     *
     * @param departmentId: the id of the department
     * @return a list of doctors in the department
     */
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Doctor>> findDoctorsByDepartment(
            @PathVariable Long departmentId
    ) {
        List<Doctor> doctorList = doctorService.findDoctorsByDepartment(departmentId);
        return new ResponseEntity<>(doctorList, HttpStatus.OK);
    }

    /**
     * Find doctors by ward
     *
     * @param wardId: the id of the ward
     * @return a list of doctors in the ward
     */
    @GetMapping("/ward/{wardId}")
    public ResponseEntity<List<Doctor>> findDoctorsByWard(
            @PathVariable Long wardId
    ) {
        List<Doctor> doctorList = doctorService.findDoctorsByWard(wardId);
        return new ResponseEntity<>(doctorList, HttpStatus.OK);
    }

    /**
     * Allocate a ward to a doctor
     *
     * @param doctorId: the id of the doctor
     * @param wardAllocation: the ward to allocate
     * @return the updated doctor
     */
    @PutMapping("/ward/{doctorId}")
    public ResponseEntity<Doctor> allocateWard(
            @PathVariable Long doctorId,
            @RequestBody WardAllocation wardAllocation
    ) {
        Doctor doctor = doctorService.allocateWard(doctorId, wardAllocation);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }
}
