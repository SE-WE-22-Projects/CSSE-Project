package com.csse.healthSphere.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Patient extends Person {
    String emergencyContactNo;
}
