package com.csse.healthSphere.dto;

import lombok.Data;

@Data
public class MedicalStaffRequest extends PersonRequest{
    int departmentId;
    int wardId;
}
