package com.csse.healthSphere.dto;

import lombok.Data;

@Data
public class BillRequest {
    String status; // TODO: need enum
    float totalPayment;
}
