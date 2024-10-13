package com.csse.healthSphere.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int departmentId;

    @OneToMany(mappedBy = "departmentId")
    private List<MedicalStaff> medicalStaff;
}
