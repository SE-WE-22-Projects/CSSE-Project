package com.csse.healthSphere.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class MedicalService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int serviceId;
    String name;
    String description;
    String category;
    float price;
}
