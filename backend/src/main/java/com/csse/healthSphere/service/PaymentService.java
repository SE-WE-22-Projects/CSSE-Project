package com.csse.healthSphere.service;

import com.csse.healthSphere.model.Payment;
import com.csse.healthSphere.dto.PaymentRequest;
import com.csse.healthSphere.repository.PaymentRepository;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private ModelMapper modelMapper;

    /**
     * Creates a payment from the given data
     *
     * @param paymentRequest the data for the new payment
     * @return the created payment
     */
    public Payment createPayment(PaymentRequest paymentRequest) {
        return null;
    }

    /**
     * Gets a list of all existing payments.
     *
     * @return a list of payments
     */
    public List<Payment> getAllPayments() {
        return List.of();
    }

    /**
     * Gets the payment with the given id.
     *
     * @param id the id of the payment
     * @return the payment
     * @throws ResourceNotFoundException if the payment does not exist
     */
    public Payment getPaymentById(Long id) {
        return null;
    }

    /**
     * Updates the payment content
     *
     * @param id             the id of the payment
     * @param paymentRequest the new data for the payment
     * @return the updated payment
     */
    public Payment updatePayment(Long id, PaymentRequest paymentRequest) {
        return null;
    }

    /**
     * Deletes a payment
     *
     * @param id the id of the payment
     */
    public void deletePayment(Long id) {

    }
}
