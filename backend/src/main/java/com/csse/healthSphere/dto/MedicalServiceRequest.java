package com.csse.healthSphere.dto;

import lombok.Data;

@Data
public class MedicalServiceRequest {
    String name;
    String description;
    String category;
    float price;
}
