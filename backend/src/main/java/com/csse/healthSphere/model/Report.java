package com.csse.healthSphere.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int reportId;
    String result;
    String status;   // TODO: need enum

    @ManyToOne
    @JoinColumn(name = "serviceId")
    MedicalService medicalService;

    @ManyToOne
    @JoinColumn(name = "patientId")
    Patient patient;
}
