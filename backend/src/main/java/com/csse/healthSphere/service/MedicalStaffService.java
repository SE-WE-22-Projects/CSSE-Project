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

    /**
     * Creates a medical staff from the given data
     *
     * @param medicalStaffRequest the data for the new medical staff
     * @return the created medical staff
     */
    public MedicalStaff createMedicalStaff(MedicalStaffRequest medicalStaffRequest){
        return null;
    }

    /**
     * Gets a list of all existing medical staffs.
     *
     * @return a list of medical staffs
     */
    public List<MedicalStaff> getAllMedicalStaffs(){
        return List.of();
    }

    /*
    * Gets the medical staff with the given id.
    
    * @param id the id of the medical staff
    * @return the medical staff
    */
    public MedicalStaff getMedicalStaffById(Long id){
        return null;
    }

    /**
     * Updates the medical staff content
     *
     * @param id the id of the medical staff
     * @param medicalStaffRequest the new data for the medical staff
     * @return the updated medical staff
     */
    public MedicalStaff updateMedicalStaff(Long id, MedicalStaffRequest medicalStaffRequest){
        return null;
    }

    /**
     * Deletes the medical staff with the given id
     *
     * @param id the id of the medical staff
     */
    public void deleteMedicalStaff(Long id){
        
    }
}
