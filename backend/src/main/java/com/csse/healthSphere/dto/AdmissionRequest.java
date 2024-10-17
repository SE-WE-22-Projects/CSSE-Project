package com.csse.healthSphere.dto;

import com.csse.healthSphere.model.Appointment;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AdmissionRequest {
    private LocalDate admissionDate;
    private LocalDate dischargeDate;
    private String description;
    private Long appointmentId;

}
