package com.csse.healthSphere.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int wardId;

    @OneToMany(mappedBy = "ward")
    private List<MedicalStaff> medicalStaff;
}
