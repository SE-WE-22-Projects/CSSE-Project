package com.csse.healthSphere.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    private String result;
    private String status;   // TODO: need enum

    @ManyToOne
    @JoinColumn(name = "service_id")
    private MedicalService medicalService;

    @ManyToOne
    @JoinColumn(name = "admission_id")
    private Admission admission;
}
