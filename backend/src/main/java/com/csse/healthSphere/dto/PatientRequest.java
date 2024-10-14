package com.csse.healthSphere.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PatientRequest extends PersonRequest {
    String emergencyContactNo;
}
