package com.csse.healthSphere.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class MedicalStaff extends Person{
    @ManyToOne
    @JoinColumn(name = "departmentId")
    Department department;

    @ManyToOne
    @JoinColumn(name = "wardId")
    Ward ward;
}
