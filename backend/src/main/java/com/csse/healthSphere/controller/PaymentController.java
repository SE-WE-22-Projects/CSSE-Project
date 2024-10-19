package com.csse.healthSphere.controller;

import com.csse.healthSphere.dto.PaymentRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Payment;
import com.csse.healthSphere.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/payment")
@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    /**
     * Create a new payment
     *
     * @param paymentRequest: the data for the new payment
     * @return the newly created payment
     */
    @PostMapping
    public ResponseEntity<Payment> createPayment(
            @RequestBody PaymentRequest paymentRequest
    ) {
        Payment createdPayment = paymentService.createPayment(paymentRequest);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    /**
     * Get all payments
     *
     * @return a list of all payments
     */
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> paymentList = paymentService.getAllPayments();
        return new ResponseEntity<>(paymentList, HttpStatus.OK);
    }

    /**
     * Get a PaymentService by id
     *
     * @param id the id of the payment
     * @return the payment for the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(
            @PathVariable Long id
    ) {
        Payment payment = paymentService.getPaymentById(id);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    /**
     * Update a payment by id
     *
     * @param id             the id of the payment
     * @param paymentRequest the updated content of the payment
     * @return the updated payment
     */
    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(
            @PathVariable Long id,
            @RequestBody PaymentRequest paymentRequest
    ) {
        Payment updatedPayment = paymentService.updatePayment(id, paymentRequest);
        return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
    }

    /**
     * Delete a payment by id
     *
     * @param id the id of the payment
     * @return an empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(
            @PathVariable Long id
    ) {
        paymentService.deletePayment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
