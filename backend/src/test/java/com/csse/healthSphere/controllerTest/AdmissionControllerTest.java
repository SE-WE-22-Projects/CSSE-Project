package com.csse.healthSphere.controllerTest;

import com.csse.healthSphere.controller.AdmissionController;
import com.csse.healthSphere.controller.BaseController;
import com.csse.healthSphere.dto.AdmissionRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Admission;
import com.csse.healthSphere.service.AdmissionService;
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
public class AdmissionControllerTest {
    @Mock
    private AdmissionService admissionService;

    @InjectMocks
    private AdmissionController admissionController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(admissionController).setControllerAdvice(new BaseController()).build();
    }

    @Test
    void createAdmission_PositiveTest() throws Exception {
        Admission admission = new Admission();
        admission.setAdmissionId(1L);

        AdmissionRequest request = new AdmissionRequest();
        request.setDescription("Routine check-up");

        when(admissionService.createAdmission(any(AdmissionRequest.class))).thenReturn(admission);

        mockMvc.perform(post("/api/admission")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.admissionId").value(admission.getAdmissionId()));
    }

    @Test
    void createAdmission_NegativeTest_AppointmentNotFound() throws Exception {
        AdmissionRequest request = new AdmissionRequest();
        request.setAppointmentId(1L);

        when(admissionService.createAdmission(any(AdmissionRequest.class)))
                .thenThrow(new ResourceNotFoundException("Appointment not found"));

        mockMvc.perform(post("/api/admission")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Appointment not found"));
    }

    @Test
    void getAllAdmissions_PositiveTest() throws Exception {
        List<Admission> admissions = List.of(new Admission());
        when(admissionService.getAllAdmissions()).thenReturn(admissions);

        mockMvc.perform(get("/api/admission"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getAllAdmissions_NegativeTest_NoAdmissionsFound() throws Exception {
        when(admissionService.getAllAdmissions()).thenReturn(List.of());

        mockMvc.perform(get("/api/admission"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getAdmissionById_PositiveTest() throws Exception {
        Admission admission = new Admission();
        admission.setAdmissionId(1L);
        when(admissionService.getAdmissionById(1L)).thenReturn(admission);

        mockMvc.perform(get("/api/admission/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.admissionId").value(1L));
    }

    @Test
    void getAdmissionById_NegativeTest_NotFound() throws Exception {
        when(admissionService.getAdmissionById(100L))
                .thenThrow(new ResourceNotFoundException("Admission not found"));

        mockMvc.perform(get("/api/admission/{id}", 100L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Admission not found"));
    }

    @Test
    void updateAdmission_PositiveTest() throws Exception {
        Admission admission = new Admission();
        admission.setAdmissionId(1L);

        AdmissionRequest request = new AdmissionRequest();
        request.setDescription("Updated description");

        when(admissionService.updateAdmission(anyLong(), any(AdmissionRequest.class))).thenReturn(admission);

        mockMvc.perform(put("/api/admission/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.admissionId").value(admission.getAdmissionId()));
    }

    @Test
    void updateAdmission_NegativeTest_NotFound() throws Exception {
        AdmissionRequest request = new AdmissionRequest();

        when(admissionService.updateAdmission(anyLong(), any(AdmissionRequest.class)))
                .thenThrow(new ResourceNotFoundException("Admission not found"));

        mockMvc.perform(put("/api/admission/{id}", 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Admission not found"));
    }

    @Test
    void deleteAdmission_PositiveTest() throws Exception {
        mockMvc.perform(delete("/api/admission/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(admissionService, times(1)).deleteAdmission(1L);
    }

    @Test
    void deleteAdmission_NegativeTest_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Admission not found")).when(admissionService).deleteAdmission(100L);

        mockMvc.perform(delete("/api/admission/{id}", 100L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Admission not found"));
    }

    @Test
    void findAdmissionByAppointment_PositiveTest() throws Exception {
        Admission admission = new Admission();
        admission.setAdmissionId(1L);
        when(admissionService.findAdmissionByAppointment(1L)).thenReturn(admission);

        mockMvc.perform(get("/api/admission/appointment/{appointmentId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.admissionId").value(admission.getAdmissionId()));
    }

    @Test
    void findAdmissionByAppointment_NegativeTest_NotFound() throws Exception {
        when(admissionService.findAdmissionByAppointment(100L))
                .thenThrow(new ResourceNotFoundException("Appointment not found"));

        mockMvc.perform(get("/api/admission/appointment/{appointmentId}", 100L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Appointment not found"));
    }

    @Test
    void findAdmissionsByPatient_PositiveTest() throws Exception {
        List<Admission> admissions = List.of(new Admission());
        when(admissionService.findAdmissionsByPatient(1L)).thenReturn(admissions);

        mockMvc.perform(get("/api/admission/patient/{patientId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void findAdmissionsByPatient_NegativeTest_NotFound() throws Exception {
        when(admissionService.findAdmissionsByPatient(1000L))
                .thenThrow(new ResourceNotFoundException("Patient not found"));

        mockMvc.perform(get("/api/admission/patient/{patientId}", 1000L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Patient not found"));
    }
}
