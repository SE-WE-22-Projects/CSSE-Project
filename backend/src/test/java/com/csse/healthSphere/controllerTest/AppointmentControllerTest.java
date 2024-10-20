package com.csse.healthSphere.controllerTest;

import com.csse.healthSphere.controller.AppointmentController;
import com.csse.healthSphere.controller.BaseController;
import com.csse.healthSphere.dto.AppointmentCreation;
import com.csse.healthSphere.dto.AppointmentRequest;
import com.csse.healthSphere.dto.AuthUser;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Appointment;
import com.csse.healthSphere.service.AppointmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController appointmentController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(appointmentController)
                .setControllerAdvice(new BaseController()).setControllerAdvice(new BaseController()).build();
    }

    @Test
    void createAppointment_PositiveTest() throws Exception {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1L);

        AppointmentRequest request = new AppointmentRequest();
        request.setDate(LocalDate.of(2024, Month.OCTOBER, 20));

        String expectedJson = "{\"date\":\"2024-10-20\"}";

        when(appointmentService.createAppointment(any(AppointmentRequest.class))).thenReturn(appointment);

        mockMvc.perform(post("/api/appointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.appointmentId").value(appointment.getAppointmentId()));
    }

    @Test
    void createAppointment_NegativeTest_PatientNotFound() throws Exception {
        AppointmentRequest request = new AppointmentRequest();
        request.setPatientId(1000L);

        when(appointmentService.createAppointment(any(AppointmentRequest.class)))
                .thenThrow(new ResourceNotFoundException("Patient not found"));

        mockMvc.perform(post("/api/appointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Patient not found"));
    }

    @Test
    void getAllAppointments_PositiveTest() throws Exception {
        List<Appointment> appointments = List.of(new Appointment());
        when(appointmentService.getAllAppointments()).thenReturn(appointments);

        mockMvc.perform(get("/api/appointment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getAllAppointments_NegativeTest_NoAppointmentsFound() throws Exception {
        when(appointmentService.getAllAppointments()).thenReturn(List.of());

        mockMvc.perform(get("/api/appointment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getAppointmentById_PositiveTest() throws Exception {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1L);
        when(appointmentService.getAppointmentById(1L)).thenReturn(appointment);

        mockMvc.perform(get("/api/appointment/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appointmentId").value(1L));
    }

    @Test
    void getAppointmentById_NegativeTest_NotFound() throws Exception {
        when(appointmentService.getAppointmentById(100L))
                .thenThrow(new ResourceNotFoundException("Appointment not found"));

        mockMvc.perform(get("/api/appointment/{id}", 100L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Appointment not found"));
    }

    @Test
    void updateAppointment_PositiveTest() throws Exception {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1L);

        String requestJson = "{\"date\":\"2024-10-20\"}";

        when(appointmentService.updateAppointment(anyLong(), any(AppointmentRequest.class)))
                .thenReturn(appointment);

        mockMvc.perform(put("/api/appointment/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appointmentId").value(appointment.getAppointmentId()));


    }

    @Test
    void updateAppointment_NegativeTest_NotFound() throws Exception {
        AppointmentRequest request = new AppointmentRequest();

        when(appointmentService.updateAppointment(anyLong(), any(AppointmentRequest.class)))
                .thenThrow(new ResourceNotFoundException("Appointment not found"));

        mockMvc.perform(put("/api/appointment/{id}", 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Appointment not found"));
    }

    @Test
    void deleteAppointment_PositiveTest() throws Exception {
        mockMvc.perform(delete("/api/appointment/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(appointmentService, times(1)).deleteAppointment(1L);
    }

    @Test
    void deleteAppointment_NegativeTest_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Appointment not found")).when(appointmentService).deleteAppointment(100L);

        mockMvc.perform(delete("/api/appointment/{id}", 100L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Appointment not found"));
    }

    @Test
    void findAppointmentsByPatient_PositiveTest() throws Exception {
        List<Appointment> appointments = List.of(new Appointment());
        when(appointmentService.findAppointmentsByPatient(1L)).thenReturn(appointments);

        mockMvc.perform(get("/api/appointment/patient/{patientId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void findAppointmentsByPatient_NegativeTest_NotFound() throws Exception {
        when(appointmentService.findAppointmentsByPatient(1000L))
                .thenThrow(new ResourceNotFoundException("Patient not found"));

        mockMvc.perform(get("/api/appointment/patient/{patientId}", 1000L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Patient not found"));
    }
}
