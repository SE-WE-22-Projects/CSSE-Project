package com.csse.healthSphere.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diagnosisId;

    @OneToOne
    @JoinColumn(name = "admission_id")
    private Admission admission;

    private String diagnosis;
    private String prescription;
}
