package com.csse.healthSphere.service;

import com.csse.healthSphere.model.Diagnosis;
import com.csse.healthSphere.dto.DiagnosisRequest;
import com.csse.healthSphere.repository.DiagnosisRepository;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiagnosisService {
    private final DiagnosisRepository diagnosisRepository;
    private ModelMapper modelMapper;

    /**
     * Creates a diagnosis from the given data
     *
     * @param diagnosisRequest the data for the new diagnosis
     * @return the created diagnosis
     */
    public Diagnosis createDiagnosis(DiagnosisRequest diagnosisRequest) {
        return null;
    }

    /**
     * Gets a list of all existing diagnosis.
     *
     * @return a list of diagnosis
     */
    public List<Diagnosis> getAllDiagnosis() {
        return List.of();
    }

    /**
     * Gets the diagnosis with the given id.
     *
     * @param id the id of the diagnosis
     * @return the diagnosis
     * @throws ResourceNotFoundException if the diagnosis does not exist
     */
    public Diagnosis getDiagnosisById(Long id) {
        return null;
    }

    /**
     * Updates the diagnosis content
     *
     * @param id               the id of the diagnosis
     * @param diagnosisRequest the new data for the diagnosis
     * @return the updated diagnosis
     */
    public Diagnosis updateDiagnosis(Long id, DiagnosisRequest diagnosisRequest) {
        return null;
    }

    /**
     * Deletes a diagnosis
     *
     * @param id the id of the diagnosis
     */
    public void deleteDiagnosis(Long id) {

    }
}
