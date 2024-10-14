package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.MedicalStaffRequest;
import com.csse.healthSphere.model.MedicalStaff;
import com.csse.healthSphere.repository.MedicalStaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalStaffService {
    private final MedicalStaffRepository medicalStaffRepository;

    public MedicalStaff createMedicalStaff(MedicalStaffRequest medicalStaffRequest){
        return null;
    }

    public List<MedicalStaff> getAllMedicalStaffs(){
        return List.of();
    }

    public MedicalStaff getMedicalStaffById(Long id){
        return null;
    }

    public MedicalStaff updateMedicalStaff(Long id, MedicalStaffRequest medicalStaffRequest){
        return null;
    }

    public void deleteMedicalStaff(Long id){

    }
}
