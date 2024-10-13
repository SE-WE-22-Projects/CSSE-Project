package com.csse.healthSphere.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Patient extends Person {
    String emergencyContactNo;
}
