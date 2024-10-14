package com.csse.healthSphere.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DoctorRequest extends MedicalStaffRequest {
    String speciality;
    String regNo;
}
