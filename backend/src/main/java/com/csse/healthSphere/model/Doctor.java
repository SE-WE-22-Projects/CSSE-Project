package com.csse.healthSphere.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Doctor extends MedicalStaff {
    String speciality;
    String regNo;
}