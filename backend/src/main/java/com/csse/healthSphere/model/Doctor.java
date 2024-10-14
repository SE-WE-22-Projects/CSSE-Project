package com.csse.healthSphere.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Doctor extends MedicalStaff {
    private String speciality;
    private String regNo;
}