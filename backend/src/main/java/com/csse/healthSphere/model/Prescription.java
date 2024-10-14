package com.csse.healthSphere.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long prescriptionId;

    @ManyToOne
    @JoinColumn(name = "patientId")
    Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    Doctor doctor;

    String diagnosis;
}
