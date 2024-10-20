package com.csse.healthSphere.controller;

import com.csse.healthSphere.dto.DiagnosisRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Diagnosis;
import com.csse.healthSphere.service.DiagnosisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/diagnosis")
@RestController
@RequiredArgsConstructor
public class DiagnosisController {
    private final DiagnosisService diagnosisService;

    /**
     * Create a new diagnosis
     *
     * @param diagnosisRequest: the data for the new diagnosis
     * @return the newly created diagnosis
     */
    @PostMapping
    public ResponseEntity<Diagnosis> createDiagnosis(
            @RequestBody DiagnosisRequest diagnosisRequest
    ) {
        Diagnosis createdDiagnosis = diagnosisService.createDiagnosis(diagnosisRequest);
        return new ResponseEntity<>(createdDiagnosis, HttpStatus.CREATED);
    }

    /**
     * Get all diagnosis
     *
     * @return a list of all diagnosis
     */
    @GetMapping
    public ResponseEntity<List<Diagnosis>> getAllDiagnosis() {
        List<Diagnosis> diagnosisList = diagnosisService.getAllDiagnosis();
        return new ResponseEntity<>(diagnosisList, HttpStatus.OK);
    }

    /**
     * Get a DiagnosisService by id
     *
     * @param id the id of the diagnosis
     * @return the diagnosis for the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Diagnosis> getDiagnosisById(
            @PathVariable Long id
    ) {
        Diagnosis diagnosis = diagnosisService.getDiagnosisById(id);
        return new ResponseEntity<>(diagnosis, HttpStatus.OK);
    }

    /**
     * Update a diagnosis by id
     *
     * @param id               the id of the diagnosis
     * @param diagnosisRequest the updated content of the diagnosis
     * @return the updated diagnosis
     */
    @PutMapping("/{id}")
    public ResponseEntity<Diagnosis> updateDiagnosis(
            @PathVariable Long id,
            @RequestBody DiagnosisRequest diagnosisRequest
    ) {
        Diagnosis updatedDiagnosis = diagnosisService.updateDiagnosis(id, diagnosisRequest);
        return new ResponseEntity<>(updatedDiagnosis, HttpStatus.OK);
    }

    /**
     * Delete a diagnosis by id
     *
     * @param id the id of the diagnosis
     * @return an empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiagnosis(
            @PathVariable Long id
    ) {
        diagnosisService.deleteDiagnosis(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
