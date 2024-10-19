package com.csse.healthSphere.dto;

import lombok.Data;

@Data
public class ReportRequest {
    String result;
    String status;
    Long serviceId;
    Long admissionId;
}
