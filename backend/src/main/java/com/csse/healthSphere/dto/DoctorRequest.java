package com.csse.healthSphere.dto;

import lombok.Data;

@Data
public class DoctorRequest extends MedicalStaffRequest {
    String speciality;
    String regNo;
}
