package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.MedicalRecordRequest;
import com.csse.healthSphere.model.MedicalRecord;
import com.csse.healthSphere.repository.MedicalRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecord createMedicalRecord(MedicalRecordRequest medicalRecordRequest){
        return null;
    }

    public List<MedicalRecord> getAllMedicalRecords(){
        return List.of();
    }

    public MedicalRecord getMedicalRecordById(Long id){
        return null;
    }

    public MedicalRecord updateMedicalRecord(Long id, MedicalRecordRequest medicalRecordRequest){
        return null;
    }

    public void deleteMedicalRecord(Long id){

    }
}
