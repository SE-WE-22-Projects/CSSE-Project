package com.csse.healthSphere.model;

import com.csse.healthSphere.enums.BillStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    private BillStatus status;
    private float total;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToMany
    private List<Charge> charges;
}
