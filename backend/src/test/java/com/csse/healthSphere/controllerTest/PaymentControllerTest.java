package com.csse.healthSphere.controllerTest;

import com.csse.healthSphere.controller.BaseController;
import com.csse.healthSphere.controller.PaymentController;
import com.csse.healthSphere.dto.PaymentRequest;
import com.csse.healthSphere.enums.PaymentType;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Payment;
import com.csse.healthSphere.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).setControllerAdvice(new BaseController()).build();
    }

    // Test for creating a payment - positive case
    @Test
    void createPayment_PositiveTest() throws Exception {
        PaymentRequest request = new PaymentRequest();
        request.setPaymentType(PaymentType.CREDIT_CARD); // assuming PaymentType is an enum

        Payment createdPayment = new Payment();
        createdPayment.setPaymentId(1L);
        createdPayment.setPaymentType(PaymentType.CREDIT_CARD);
        createdPayment.setTotalAmount(100.0);
        createdPayment.setPaymentDate(LocalDate.now());

        when(paymentService.createPayment(any(PaymentRequest.class))).thenReturn(createdPayment);

        mockMvc.perform(post("/api/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.paymentId").value(1L))
                .andExpect(jsonPath("$.paymentType").value("CREDIT_CARD"));
    }

    // Test for getting all payments - positive case
    @Test
    void getAllPayments_PositiveTest() throws Exception {
        List<Payment> paymentList = List.of(new Payment());

        when(paymentService.getAllPayments()).thenReturn(paymentList);

        mockMvc.perform(get("/api/payment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    // Test for getting all payments - negative case (empty list)
    @Test
    void getAllPayments_NegativeTest_EmptyList() throws Exception {
        when(paymentService.getAllPayments()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/payment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // Test for getting a payment by id - positive case
    @Test
    void getPaymentById_PositiveTest() throws Exception {
        Payment payment = new Payment();
        payment.setPaymentId(1L);
        payment.setTotalAmount(100.0);
        payment.setPaymentType(PaymentType.CREDIT_CARD);
        payment.setPaymentDate(LocalDate.now());

        when(paymentService.getPaymentById(1L)).thenReturn(payment);

        mockMvc.perform(get("/api/payment/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value(1L))
                .andExpect(jsonPath("$.totalAmount").value(100.0));
    }

    // Test for getting a payment by id - negative case (not found)
    @Test
    void getPaymentById_NegativeTest_NotFound() throws Exception {
        when(paymentService.getPaymentById(1L)).thenThrow(new ResourceNotFoundException("Payment not found"));

        mockMvc.perform(get("/api/payment/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Payment not found"));
    }

    // Test for updating a payment - positive case
    @Test
    void updatePayment_PositiveTest() throws Exception {
        PaymentRequest request = new PaymentRequest();
        request.setPaymentType(PaymentType.DEBIT_CARD); // assuming PaymentType is an enum

        Payment updatedPayment = new Payment();
        updatedPayment.setPaymentId(1L);
        updatedPayment.setPaymentType(PaymentType.DEBIT_CARD);

        when(paymentService.updatePayment(anyLong(), any(PaymentRequest.class))).thenReturn(updatedPayment);

        mockMvc.perform(put("/api/payment/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value(1L))
                .andExpect(jsonPath("$.paymentType").value("DEBIT_CARD"));
    }

    // Test for updating a payment - negative case (not found)
    @Test
    void updatePayment_NegativeTest_NotFound() throws Exception {
        PaymentRequest request = new PaymentRequest();
        request.setPaymentType(PaymentType.CASH); // assuming PaymentType is an enum

        when(paymentService.updatePayment(anyLong(), any(PaymentRequest.class)))
                .thenThrow(new ResourceNotFoundException("Payment not found"));

        mockMvc.perform(put("/api/payment/{id}", 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Payment not found"));
    }

    // Test for deleting a payment - positive case
    @Test
    void deletePayment_PositiveTest() throws Exception {
        mockMvc.perform(delete("/api/payment/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(paymentService, times(1)).deletePayment(1L);
    }

    // Test for deleting a payment - negative case (not found)
    @Test
    void deletePayment_NegativeTest_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Payment not found")).when(paymentService).deletePayment(100L);

        mockMvc.perform(delete("/api/payment/{id}", 100L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Payment not found"));
    }
}
