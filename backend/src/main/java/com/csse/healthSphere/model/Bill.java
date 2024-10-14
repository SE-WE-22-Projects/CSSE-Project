package com.csse.healthSphere.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    private String status; // TODO: need enum
    private float total;
    private String type; // TODO: need enum

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToMany
    private List<Charge> charges;
}
