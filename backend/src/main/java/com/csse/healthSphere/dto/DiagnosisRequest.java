package com.csse.healthSphere.dto;

import lombok.Data;

@Data
public class DiagnosisRequest {
    private Long patientId;
    private Long doctorId;
    private Long appointmentId;
    private String diagnosis;
    private String prescription;
}
