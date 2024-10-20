package com.csse.healthSphere.controllerTest;

import com.csse.healthSphere.controller.BaseController;
import com.csse.healthSphere.controller.DoctorController;
import com.csse.healthSphere.dto.DoctorRequest;
import com.csse.healthSphere.dto.WardAllocation;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Doctor;
import com.csse.healthSphere.service.DoctorService;
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
public class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private DoctorController doctorController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(doctorController).setControllerAdvice(new BaseController()).build();
    }

    // Test for getting all doctors - positive case
    @Test
    void getAllDoctors_PositiveTest() throws Exception {
        List<Doctor> doctors = List.of(new Doctor());

        when(doctorService.getAllDoctors()).thenReturn(doctors);

        mockMvc.perform(get("/api/doctor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    // Test for getting all doctors - negative case (no data available)
    @Test
    void getAllDoctors_NegativeTest_EmptyList() throws Exception {
        when(doctorService.getAllDoctors()).thenReturn(List.of());

        mockMvc.perform(get("/api/doctor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // Test for getting doctor by id - positive case
    @Test
    void getDoctorById_PositiveTest() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setPersonId(1L);
        doctor.setSpeciality("Pediatrics");

        when(doctorService.getDoctorById(1L)).thenReturn(doctor);

        mockMvc.perform(get("/api/doctor/{id}", 1L))
                .andExpect(status().isOk());
    }

    // Test for getting doctor by id - negative case (not found)
    @Test
    void getDoctorById_NegativeTest_NotFound() throws Exception {
        when(doctorService.getDoctorById(1L)).thenThrow(new ResourceNotFoundException("Doctor not found"));

        mockMvc.perform(get("/api/doctor/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Doctor not found"));
    }

    // Test for updating a doctor - negative case (doctor not found)
    @Test
    void updateDoctor_NegativeTest_NotFound() throws Exception {
        DoctorRequest request = new DoctorRequest();
        request.setSpeciality("Neurology");

        when(doctorService.updateDoctor(anyLong(), any(DoctorRequest.class)))
                .thenThrow(new ResourceNotFoundException("Doctor not found"));

        mockMvc.perform(put("/api/doctor/{id}", 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Doctor not found"));
    }

    // Test for deleting a doctor - positive case
    @Test
    void deleteDoctor_PositiveTest() throws Exception {
        mockMvc.perform(delete("/api/doctor/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(doctorService, times(1)).deleteDoctor(1L);
    }

    // Test for deleting a doctor - negative case (doctor not found)
    @Test
    void deleteDoctor_NegativeTest_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Doctor not found")).when(doctorService).deleteDoctor(100L);

        mockMvc.perform(delete("/api/doctor/{id}", 100L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Doctor not found"));
    }

    // Test for finding doctors by department - positive case
    @Test
    void findDoctorsByDepartment_PositiveTest() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setPersonId(1L);
        doctor.setSpeciality("Dermatology");

        List<Doctor> doctorList = List.of(doctor);

        when(doctorService.findDoctorsByDepartment(1L)).thenReturn(doctorList);

        mockMvc.perform(get("/api/doctor/department/{departmentId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].speciality").value("Dermatology"));
    }

    // Test for finding doctors by department - negative case (no doctors found)
    @Test
    void findDoctorsByDepartment_NegativeTest_EmptyList() throws Exception {
        when(doctorService.findDoctorsByDepartment(1L)).thenReturn(List.of());

        mockMvc.perform(get("/api/doctor/department/{departmentId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // Test for finding doctors by ward - positive case
    @Test
    void findDoctorsByWard_PositiveTest() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setPersonId(1L);
        doctor.setSpeciality("General Surgery");

        List<Doctor> doctorList = List.of(doctor);

        when(doctorService.findDoctorsByWard(1L)).thenReturn(doctorList);

        mockMvc.perform(get("/api/doctor/ward/{wardId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].speciality").value("General Surgery"));
    }

    // Test for finding doctors by ward - negative case (no doctors found)
    @Test
    void findDoctorsByWard_NegativeTest_EmptyList() throws Exception {
        when(doctorService.findDoctorsByWard(1L)).thenReturn(List.of());

        mockMvc.perform(get("/api/doctor/ward/{wardId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // Test for allocating ward to doctor - negative case (doctor not found)
    @Test
    void allocateWard_NegativeTest_NotFound() throws Exception {
        WardAllocation allocation = new WardAllocation();
        allocation.setWardId(100L);

        mockMvc.perform(post("/api/doctor/{id}/ward", 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(allocation)))
                .andExpect(status().isNotFound());
    }
}
