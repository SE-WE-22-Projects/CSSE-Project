package com.csse.healthSphere.controller;

import com.csse.healthSphere.dto.DoctorRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Doctor;
import com.csse.healthSphere.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/doctor")
@RestController
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    // create doctor
    @PostMapping
    public ResponseEntity<Doctor> createDoctor(
            @RequestBody DoctorRequest doctorRequest
            ){
        Doctor createdDoctor = doctorService.createDoctor(doctorRequest);
        return new ResponseEntity<>(createdDoctor, HttpStatus.CREATED);
    }

    // get all doctors
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors(){
        List<Doctor> doctorList = doctorService.getAllDoctors();
        return new ResponseEntity<>(doctorList,HttpStatus.OK);
    }

    // get doctor by id
    @GetMapping("/{id}")
    public  ResponseEntity<Doctor> getDoctorById(
            @PathVariable Long id
    ){
        Doctor doctor = doctorService.getDoctorById(id);
        return new ResponseEntity<>(doctor,HttpStatus.OK);
    }

    // update doctor by id
    @PutMapping("/{id}")
    public ResponseEntity<Doctor>  updateDoctor(
            @PathVariable Long id,
            @RequestBody DoctorRequest doctorRequest
    ){
        Doctor updatedDoctor = doctorService.updateDoctor(id,doctorRequest);
        return new ResponseEntity<>(updatedDoctor,HttpStatus.OK);
    }

    // delete doctor by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(Long id){
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Exception handler for ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
