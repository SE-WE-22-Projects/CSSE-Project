package com.csse.healthSphere.dto;

import lombok.Data;

@Data
public class PrescriptionRequest {
    int patientId;
    int doctorId;
    String diagnosis;
}
