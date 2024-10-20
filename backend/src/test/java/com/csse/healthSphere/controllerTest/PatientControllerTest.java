package com.csse.healthSphere.controllerTest;

import com.csse.healthSphere.controller.BaseController;
import com.csse.healthSphere.controller.PatientController;
import com.csse.healthSphere.dto.PatientRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Patient;
import com.csse.healthSphere.service.PatientService;
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
public class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).setControllerAdvice(new BaseController()).build();
    }

    // Test for creating a patient - positive case
    @Test
    void createPatient_PositiveTest() throws Exception {
        PatientRequest request = new PatientRequest();
        request.setName("Saman Kumara");
        request.setDob(LocalDate.of(2001, 9, 23));
        request.setAddress("246 Main St");

        String requestJson = "{\"name\":\"Saman Kumara\",\"dob\":\"2001-09-23\",\"address\":\"246 Main St\"}";

        Patient createdPatient = new Patient();
        createdPatient.setPersonId(1L);
        createdPatient.setName("Saman Kumara");
        createdPatient.setDob(LocalDate.of(2001, 9, 23));
        createdPatient.setAddress("246 Main St");

        when(patientService.createPatient(any(PatientRequest.class))).thenReturn(createdPatient);

        mockMvc.perform(post("/api/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());
    }

    // Test for getting all patients - positive case
    @Test
    void getAllPatients_PositiveTest() throws Exception {
        List<Patient> patientList = List.of(new Patient());

        when(patientService.getAllPatients()).thenReturn(patientList);

        mockMvc.perform(get("/api/patient"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    // Test for getting all patients - negative case (empty list)
    @Test
    void getAllPatients_NegativeTest_EmptyList() throws Exception {
        when(patientService.getAllPatients()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/patient"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // Test for getting a patient by id - positive case
    @Test
    void getPatientById_PositiveTest() throws Exception {
        Patient patient = new Patient();
        patient.setPersonId(1L);
        patient.setName("John Doe");

        when(patientService.getPatientById(1L)).thenReturn(patient);

        mockMvc.perform(get("/api/patient/{id}", 1L))
                .andExpect(status().isOk());
    }

    // Test for getting a patient by id - negative case (not found)
    @Test
    void getPatientById_NegativeTest_NotFound() throws Exception {
        when(patientService.getPatientById(1L)).thenThrow(new ResourceNotFoundException("Patient not found"));

        mockMvc.perform(get("/api/patient/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Patient not found"));
    }

    // Test for updating a patient - positive case
    @Test
    void updatePatient_PositiveTest() throws Exception {
        PatientRequest request = new PatientRequest();
        request.setName("Jane Doe");
        LocalDate.of(2001, 9, 23);
        request.setAddress("456 Main St");

        Patient updatedPatient = new Patient();
        updatedPatient.setPersonId(1L);
        updatedPatient.setName("Jane Doe");

        when(patientService.updatePatient(anyLong(), any(PatientRequest.class))).thenReturn(updatedPatient);

        mockMvc.perform(put("/api/patient/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    // Test for updating a patient - negative case (not found)
    @Test
    void updatePatient_NegativeTest_NotFound() throws Exception {
        PatientRequest request = new PatientRequest();
        request.setName("Jane Doe");

        when(patientService.updatePatient(anyLong(), any(PatientRequest.class)))
                .thenThrow(new ResourceNotFoundException("Patient not found"));

        mockMvc.perform(put("/api/patient/{id}", 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Patient not found"));
    }

    // Test for deleting a patient - positive case
    @Test
    void deletePatient_PositiveTest() throws Exception {
        mockMvc.perform(delete("/api/patient/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(patientService, times(1)).deletePatient(1L);
    }

    // Test for deleting a patient - negative case (not found)
    @Test
    void deletePatient_NegativeTest_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Patient not found")).when(patientService).deletePatient(100L);

        mockMvc.perform(delete("/api/patient/{id}", 100L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Patient not found"));
    }
}
