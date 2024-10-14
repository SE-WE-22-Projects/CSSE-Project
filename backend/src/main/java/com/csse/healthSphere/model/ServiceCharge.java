package com.csse.healthSphere.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("service")
public class ServiceCharge extends Charge {
    @OneToOne
    @JoinColumn(name = "report_id")
    private Report report;
}
