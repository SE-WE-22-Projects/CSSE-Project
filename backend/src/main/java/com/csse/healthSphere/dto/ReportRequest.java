package com.csse.healthSphere.dto;

import lombok.Data;

@Data
public class ReportRequest {
    String result;
    String status;
    int serviceId;
    int patientId;
}
