package com.csse.healthSphere.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Admission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate admissionDate;
    private LocalDate dischargeDate;
    private String description;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @OneToOne
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;

    @OneToMany
    private List<Report> reports;
}
