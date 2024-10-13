package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.PrescriptionRequest;
import com.csse.healthSphere.model.Prescription;
import com.csse.healthSphere.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;

    public Prescription createPrescription(PrescriptionRequest prescriptionRequest){
        return null;
    }

    public List<Prescription> getAllPrescriptions(){
        return List.of();
    }

    public Prescription getPrescriptionById(Long id){
        return null;
    }

    public Prescription updatePrescription(Long id, PrescriptionRequest prescriptionRequest){
        return null;
    }

    public void deletePrescription(Long id){

    }
}
