package com.csse.healthSphere.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class AppointmentCharge extends Charge {
    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
}
