package com.csse.healthSphere.service;

import com.csse.healthSphere.model.*;
import com.csse.healthSphere.dto.DiagnosisRequest;
import com.csse.healthSphere.repository.*;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiagnosisService {
    private final DiagnosisRepository diagnosisRepository;
    private final AdmissionRepository admissionRepository;
    private final ModelMapper modelMapper;

    /**
     * Creates a diagnosis from the given data
     *
     * @param diagnosisRequest the data for the new diagnosis
     * @return the created diagnosis
     */
    public Diagnosis createDiagnosis(DiagnosisRequest diagnosisRequest) {
        Admission admission = admissionRepository.findById(diagnosisRequest.getAdmissionId())
                .orElseThrow(() -> new ResourceNotFoundException("Admission not found"));

        Diagnosis diagnosis = modelMapper.map(diagnosisRequest, Diagnosis.class);
        diagnosis.setDiagnosisId(null);
        diagnosis.setAdmission(admission);
        return diagnosisRepository.save(diagnosis);
    }

    /**
     * Gets a list of all existing diagnosis.
     *
     * @return a list of diagnosis
     */
    public List<Diagnosis> getAllDiagnosis() {
        return diagnosisRepository.findAll();
    }

    /**
     * Gets the diagnosis with the given id.
     *
     * @param id the id of the diagnosis
     * @return the diagnosis
     * @throws ResourceNotFoundException if the diagnosis does not exist
     */
    public Diagnosis getDiagnosisById(Long id) {
        return diagnosisRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Diagnosis not found"));
    }

    /**
     * Updates the diagnosis content
     *
     * @param id               the id of the diagnosis
     * @param diagnosisRequest the new data for the diagnosis
     * @return the updated diagnosis
     */
    public Diagnosis updateDiagnosis(Long id, DiagnosisRequest diagnosisRequest) {
        Diagnosis diagnosis = diagnosisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diagnosis not found"));
        diagnosis.setDiagnosis(diagnosisRequest.getDiagnosis());
        diagnosis.setPrescription(diagnosisRequest.getPrescription());

        return diagnosisRepository.save(diagnosis);
    }

    /**
     * Deletes a diagnosis
     *
     * @param id the id of the diagnosis
     */
    public void deleteDiagnosis(Long id) {
        Diagnosis diagnosis = diagnosisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diagnosis not found"));
        diagnosisRepository.delete(diagnosis);
    }


}
