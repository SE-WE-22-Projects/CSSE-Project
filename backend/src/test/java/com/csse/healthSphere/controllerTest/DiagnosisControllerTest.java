package com.csse.healthSphere.controllerTest;

import com.csse.healthSphere.controller.BaseController;
import com.csse.healthSphere.controller.DiagnosisController;
import com.csse.healthSphere.dto.DiagnosisRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Diagnosis;
import com.csse.healthSphere.service.DiagnosisService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class DiagnosisControllerTest {

    @Mock
    private DiagnosisService diagnosisService;

    @InjectMocks
    private DiagnosisController diagnosisController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(diagnosisController).setControllerAdvice(new BaseController()).build();
    }

    // Test for creating a diagnosis - positive case
    @Test
    void createDiagnosis_PositiveTest() throws Exception {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDiagnosisId(1L);
        diagnosis.setDiagnosis("Flu");
        diagnosis.setPrescription("Rest and Flu medication");

        DiagnosisRequest request = new DiagnosisRequest();
        request.setAdmissionId(1L);
        request.setDiagnosis("Flu");
        request.setPrescription("Rest and Flu medication");

        when(diagnosisService.createDiagnosis(any(DiagnosisRequest.class))).thenReturn(diagnosis);

        mockMvc.perform(post("/api/diagnosis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.diagnosisId").value(diagnosis.getDiagnosisId()))
                .andExpect(jsonPath("$.diagnosis").value("Flu"))
                .andExpect(jsonPath("$.prescription").value("Rest and Flu medication"));
    }

    // Test for getting all diagnoses - positive case
    @Test
    void getAllDiagnosis_PositiveTest() throws Exception {
        List<Diagnosis> diagnoses = List.of(new Diagnosis());

        when(diagnosisService.getAllDiagnosis()).thenReturn(diagnoses);

        mockMvc.perform(get("/api/diagnosis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    // Test for getting all diagnoses - negative case (no data available)
    @Test
    void getAllDiagnosis_NegativeTest_EmptyList() throws Exception {
        when(diagnosisService.getAllDiagnosis()).thenReturn(List.of());

        mockMvc.perform(get("/api/diagnosis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // Test for getting diagnosis by id - positive case
    @Test
    void getDiagnosisById_PositiveTest() throws Exception {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDiagnosisId(1L);
        diagnosis.setDiagnosis("COVID-19");

        when(diagnosisService.getDiagnosisById(1L)).thenReturn(diagnosis);

        mockMvc.perform(get("/api/diagnosis/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diagnosisId").value(1L))
                .andExpect(jsonPath("$.diagnosis").value("COVID-19"));
    }

    // Test for getting diagnosis by id - negative case (not found)
    @Test
    void getDiagnosisById_NegativeTest_NotFound() throws Exception {
        when(diagnosisService.getDiagnosisById(1L)).thenThrow(new ResourceNotFoundException("Diagnosis not found"));

        mockMvc.perform(get("/api/diagnosis/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Diagnosis not found"));
    }

    // Test for updating a diagnosis - positive case
    @Test
    void updateDiagnosis_PositiveTest() throws Exception {
        Diagnosis updatedDiagnosis = new Diagnosis();
        updatedDiagnosis.setDiagnosisId(1L);
        updatedDiagnosis.setDiagnosis("Migraine");
        updatedDiagnosis.setPrescription("Painkillers");

        DiagnosisRequest request = new DiagnosisRequest();
        request.setDiagnosis("Migraine");
        request.setPrescription("Painkillers");

        when(diagnosisService.updateDiagnosis(anyLong(), any(DiagnosisRequest.class))).thenReturn(updatedDiagnosis);

        mockMvc.perform(put("/api/diagnosis/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diagnosisId").value(1L))
                .andExpect(jsonPath("$.diagnosis").value("Migraine"))
                .andExpect(jsonPath("$.prescription").value("Painkillers"));
    }

    // Test for updating a diagnosis - negative case (diagnosis not found)
    @Test
    void updateDiagnosis_NegativeTest_NotFound() throws Exception {
        DiagnosisRequest request = new DiagnosisRequest();
        request.setDiagnosis("Diabetes");
        request.setPrescription("Insulin");

        when(diagnosisService.updateDiagnosis(anyLong(), any(DiagnosisRequest.class)))
                .thenThrow(new ResourceNotFoundException("Diagnosis not found"));

        mockMvc.perform(put("/api/diagnosis/{id}", 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Diagnosis not found"));
    }

    // Test for deleting a diagnosis - positive case
    @Test
    void deleteDiagnosis_PositiveTest() throws Exception {
        mockMvc.perform(delete("/api/diagnosis/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(diagnosisService, times(1)).deleteDiagnosis(1L);
    }

    // Test for deleting a diagnosis - negative case (diagnosis not found)
    @Test
    void deleteDiagnosis_NegativeTest_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Diagnosis not found")).when(diagnosisService).deleteDiagnosis(100L);

        mockMvc.perform(delete("/api/diagnosis/{id}", 100L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Diagnosis not found"));
    }

    // Test for finding diagnosis by appointment id - positive case
    @Test
    void findDiagnosisByAdmission_PositiveTest() throws Exception {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDiagnosisId(1L);
        diagnosis.setDiagnosis("COVID-19");

        when(diagnosisService.findDiagnosisByAppointment(1L)).thenReturn(diagnosis);

        mockMvc.perform(get("/api/diagnosis/appointment/{appointmentId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diagnosisId").value(1L))
                .andExpect(jsonPath("$.diagnosis").value("COVID-19"));
    }

    // Test for finding diagnosis by appointment id - negative case (diagnosis not found)
    @Test
    void findDiagnosisByAppointment_NegativeTest_NotFound() throws Exception {
        when(diagnosisService.findDiagnosisByAppointment(1L)).thenThrow(new ResourceNotFoundException("Diagnosis not found"));

        mockMvc.perform(get("/api/diagnosis/appointment/{appointmentId}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Diagnosis not found"));
    }


}

