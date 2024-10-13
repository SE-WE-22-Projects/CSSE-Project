package com.csse.healthSphere.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicalRecordRequest {
    int patientId;
    int doctorId;
    LocalDate admissionDate;
    LocalDate dischargeDate;
    String description;

    // TODO: need to add prescriptions list
}
