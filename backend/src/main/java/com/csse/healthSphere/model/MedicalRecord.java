package com.csse.healthSphere.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int recordId;

    @OneToOne
    @JoinColumn(name = "patientId")
    Patient patient;

    @OneToOne
    @JoinColumn(name = "doctorId")
    Doctor doctor;

    LocalDate admissionDate;
    LocalDate dischargeDate;
    String description;

    // TODO: need to add prescriptions list
}
