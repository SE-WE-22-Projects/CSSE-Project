package com.csse.healthSphere.dto;

import lombok.Data;

@Data
public class PatientRequest extends PersonRequest {
    String emergencyContactNo;
}
