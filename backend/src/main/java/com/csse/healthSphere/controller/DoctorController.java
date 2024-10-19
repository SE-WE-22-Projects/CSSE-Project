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

    // create doctor
    @PostMapping
    public ResponseEntity<Doctor> createDoctor(
            @RequestBody DoctorRequest doctorRequest
    ) {
        Doctor createdDoctor = doctorService.createDoctor(doctorRequest);
        return new ResponseEntity<>(createdDoctor, HttpStatus.CREATED);
    }

    // get all doctors
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctorList = doctorService.getAllDoctors();
        return new ResponseEntity<>(doctorList, HttpStatus.OK);
    }

    // get doctor by id
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(
            @PathVariable Long id
    ) {
        Doctor doctor = doctorService.getDoctorById(id);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    // update doctor by id
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(
            @PathVariable Long id,
            @RequestBody DoctorRequest doctorRequest
    ) {
        Doctor updatedDoctor = doctorService.updateDoctor(id, doctorRequest);
        return new ResponseEntity<>(updatedDoctor, HttpStatus.OK);
    }

    // delete doctor by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(
            @PathVariable Long id
    ) {
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Doctor>> findDoctorsByDepartment(
            @PathVariable Long departmentId
    ) {
        List<Doctor> doctorList = doctorService.findDoctorsByDepartment(departmentId);
        return new ResponseEntity<>(doctorList, HttpStatus.OK);
    }

    @GetMapping("/ward/{wardId}")
    public ResponseEntity<List<Doctor>> findDoctorsByWard(
            @PathVariable Long wardId
    ) {
        List<Doctor> doctorList = doctorService.findDoctorsByWard(wardId);
        return new ResponseEntity<>(doctorList, HttpStatus.OK);
    }

    @PutMapping("/ward/{doctorId}")
    public ResponseEntity<Doctor> allocateWard(
            @PathVariable Long doctorId,
            @RequestBody WardAllocation wardAllocation
    ) {
        Doctor doctor = doctorService.allocateWard(doctorId, wardAllocation);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }
}
