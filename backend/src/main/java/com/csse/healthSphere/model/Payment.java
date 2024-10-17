package com.csse.healthSphere.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Payment {
    @Id
    private Long paymentId;

    private String paymentType;
    private LocalDate paymentDate;
}


// TODO: no need any more