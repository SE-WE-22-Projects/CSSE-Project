package com.csse.healthSphere.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diagnosisId;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    private String diagnosis;
    private String prescription;
}
