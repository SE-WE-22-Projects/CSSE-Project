package com.csse.healthSphere.model;

import com.csse.healthSphere.enums.PaymentType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

/**
 * Store all charges in the appointment class.
 * When the patient pays for the appointment, calculate the total and create a Payment and set it to the Appointment class
 * payment == null => not paid
 * payment != null => fully paid
 */

@Data
@Entity
public class Payment {
    @Id
    private Long paymentId;

    private double totalAmount;
    private PaymentType paymentType;
    private LocalDate paymentDate;
}


