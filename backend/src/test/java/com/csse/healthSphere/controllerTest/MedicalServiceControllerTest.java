package com.csse.healthSphere.controllerTest;

import com.csse.healthSphere.controller.BaseController;
import com.csse.healthSphere.controller.MedicalServiceController;
import com.csse.healthSphere.dto.MedicalServiceRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.MedicalService;
import com.csse.healthSphere.service.MedicalServiceService;
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

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class MedicalServiceControllerTest {

    @Mock
    private MedicalServiceService medicalServiceService;

    @InjectMocks
    private MedicalServiceController medicalServiceController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(medicalServiceController).setControllerAdvice(new BaseController()).build();
    }

    // Test for creating a medical service - positive case
    @Test
    void createService_PositiveTest() throws Exception {
        MedicalServiceRequest request = new MedicalServiceRequest();
        request.setName("Consultation");
        request.setDescription("General consultation services");
        request.setCategory("General");
        request.setPrice(50.0f);

        MedicalService createdService = new MedicalService();
        createdService.setServiceId(1L);
        createdService.setName("Consultation");
        createdService.setDescription("General consultation services");
        createdService.setCategory("General");
        createdService.setPrice(50.0f);

        when(medicalServiceService.createService(any(MedicalServiceRequest.class))).thenReturn(createdService);

        mockMvc.perform(post("/api/service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.serviceId").value(1L))
                .andExpect(jsonPath("$.name").value("Consultation"));
    }

    // Test for getting all medical services - positive case
    @Test
    void getAllServices_PositiveTest() throws Exception {
        List<MedicalService> medicalServiceList = List.of(new MedicalService());

        when(medicalServiceService.getAllServices()).thenReturn(medicalServiceList);

        mockMvc.perform(get("/api/service"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    // Test for getting all medical services - negative case (empty list)
    @Test
    void getAllServices_NegativeTest_EmptyList() throws Exception {
        when(medicalServiceService.getAllServices()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/service"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // Test for getting a medical service by id - positive case
    @Test
    void getServiceById_PositiveTest() throws Exception {
        MedicalService medicalService = new MedicalService();
        medicalService.setServiceId(1L);
        medicalService.setName("Consultation");

        when(medicalServiceService.getServiceById(1L)).thenReturn(medicalService);

        mockMvc.perform(get("/api/service/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceId").value(1L))
                .andExpect(jsonPath("$.name").value("Consultation"));
    }

    // Test for getting a medical service by id - negative case (not found)
    @Test
    void getServiceById_NegativeTest_NotFound() throws Exception {
        when(medicalServiceService.getServiceById(1L)).thenThrow(new ResourceNotFoundException("Service not found"));

        mockMvc.perform(get("/api/service/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Service not found"));
    }

    // Test for updating a medical service - positive case
    @Test
    void updateService_PositiveTest() throws Exception {
        MedicalServiceRequest request = new MedicalServiceRequest();
        request.setName("Updated Consultation");
        request.setDescription("Updated description");
        request.setCategory("General");
        request.setPrice(75.0f);

        MedicalService updatedService = new MedicalService();
        updatedService.setServiceId(1L);
        updatedService.setName("Updated Consultation");

        when(medicalServiceService.updateService(anyLong(), any(MedicalServiceRequest.class))).thenReturn(updatedService);

        mockMvc.perform(put("/api/service/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceId").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Consultation"));
    }

    // Test for updating a medical service - negative case (service not found)
    @Test
    void updateService_NegativeTest_NotFound() throws Exception {
        MedicalServiceRequest request = new MedicalServiceRequest();
        request.setName("Updated Consultation");

        when(medicalServiceService.updateService(anyLong(), any(MedicalServiceRequest.class)))
                .thenThrow(new ResourceNotFoundException("Service not found"));

        mockMvc.perform(put("/api/service/{id}", 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Service not found"));
    }

    // Test for deleting a medical service - positive case
    @Test
    void deleteService_PositiveTest() throws Exception {
        mockMvc.perform(delete("/api/service/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(medicalServiceService, times(1)).deleteService(1L);
    }

    // Test for deleting a medical service - negative case (service not found)
    @Test
    void deleteService_NegativeTest_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Service not found")).when(medicalServiceService).deleteService(100L);

        mockMvc.perform(delete("/api/service/{id}", 100L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Service not found"));
    }
}
