package com.csse.healthSphere.dto;

import lombok.Data;

@Data
public class DiagnosisRequest {
    private Long admissionId;
    private String diagnosis;
    private String prescription;
}
