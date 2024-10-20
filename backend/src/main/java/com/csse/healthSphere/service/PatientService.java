package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.PatientRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Patient;
import com.csse.healthSphere.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    /**
     * Creates a patient from the given data
     *
     * @param patientRequest the data for the new patient
     * @return the created patient
     */
    public Patient createPatient(PatientRequest patientRequest) {
        Patient patient = modelMapper.map(patientRequest, Patient.class);
        patient.setPersonId(null);
        return patientRepository.save(patient);
    }

    /**
     * Gets a list of all existing patients.
     *
     * @return a list of patients
     */
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    /**
     * Gets the patient with the given id.
     *
     * @param id the id of the patient
     * @return the patient
     * @throws ResourceNotFoundException if the patient does not exist
     */
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
    }

    /**
     * Updates the patient content
     *
     * @param id            the id of the patient
     * @param patientRequest the new data for the patient
     * @return the updated patient
     */
    public Patient updatePatient(Long id, PatientRequest patientRequest) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        modelMapper.map(patientRequest, patient);

        return patientRepository.save(patient);
    }

    /**
     * Deletes a patient
     *
     * @param id the id of the patient
     */
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        patientRepository.delete(patient);
    }
}
