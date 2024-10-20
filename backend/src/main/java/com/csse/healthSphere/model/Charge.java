package com.csse.healthSphere.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Charge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chargeId;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    private Float amount;
}
