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
        Payment payment = modelMapper.map(paymentRequest, Payment.class);
        payment.setPaymentId(null);
        return paymentRepository.save(payment);
    }

    /**
     * Gets a list of all existing payments.
     *
     * @return a list of payments
     */
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    /**
     * Gets the payment with the given id.
     *
     * @param id the id of the payment
     * @return the payment
     * @throws ResourceNotFoundException if the payment does not exist
     */
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
    }

    /**
     * Updates the payment content
     *
     * @param id             the id of the payment
     * @param paymentRequest the new data for the payment
     * @return the updated payment
     */
    public Payment updatePayment(Long id, PaymentRequest paymentRequest) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        payment.setPaymentType(paymentRequest.getPaymentType());
        return paymentRepository.save(payment);
    }

    /**
     * Deletes a payment
     *
     * @param id the id of the payment
     */
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        paymentRepository.delete(payment);
    }
}
