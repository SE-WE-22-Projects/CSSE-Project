package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.PatientRequest;
import com.csse.healthSphere.model.Patient;
import com.csse.healthSphere.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public Patient createPatient(PatientRequest patientRequest){
        return null;
    }

    public List<Patient> getAllPatients(){
        return List.of();
    }

    public Patient getPatientById(Long id){
        return null;
    }
    public Patient updatePatient(Long id, PatientRequest patientRequest){
        return null;
    }
    public void deletePatient(Long id){

    }
}
