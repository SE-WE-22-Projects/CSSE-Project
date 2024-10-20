package com.csse.healthSphere.controllerTest;

import com.csse.healthSphere.controller.BaseController;
import com.csse.healthSphere.controller.WardController;
import com.csse.healthSphere.dto.WardRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Ward;
import com.csse.healthSphere.service.WardService;
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
public class WardControllerTest {

    @Mock
    private WardService wardService;

    @InjectMocks
    private WardController wardController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(wardController).setControllerAdvice(new BaseController()).build();
    }

    // Test for creating a ward - positive case
    @Test
    void createWard_PositiveTest() throws Exception {
        WardRequest request = new WardRequest();
        request.setName("Ward A");
        request.setCapacity(20);
        request.setLocation("Ground Floor");

        Ward createdWard = new Ward();
        createdWard.setWardId(1L);
        createdWard.setName(request.getName());
        createdWard.setCapacity(request.getCapacity());
        createdWard.setLocation(request.getLocation());

        when(wardService.createWard(any(WardRequest.class))).thenReturn(createdWard);

        mockMvc.perform(post("/api/ward")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.wardId").value(1L))
                .andExpect(jsonPath("$.name").value("Ward A"));
    }

    // Test for getting all wards - positive case
    @Test
    void getAllWards_PositiveTest() throws Exception {
        List<Ward> wardList = List.of(new Ward());

        when(wardService.getAllWards()).thenReturn(wardList);

        mockMvc.perform(get("/api/ward"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    // Test for getting all wards - negative case (empty list)
    @Test
    void getAllWards_NegativeTest_EmptyList() throws Exception {
        when(wardService.getAllWards()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/ward"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // Test for getting a ward by id - positive case
    @Test
    void getWardById_PositiveTest() throws Exception {
        Ward ward = new Ward();
        ward.setWardId(1L);
        ward.setName("Ward A");
        ward.setCapacity(20);
        ward.setLocation("Ground Floor");

        when(wardService.getWardById(1L)).thenReturn(ward);

        mockMvc.perform(get("/api/ward/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wardId").value(1L))
                .andExpect(jsonPath("$.name").value("Ward A"));
    }

    // Test for getting a ward by id - negative case (not found)
    @Test
    void getWardById_NegativeTest_NotFound() throws Exception {
        when(wardService.getWardById(1L)).thenThrow(new ResourceNotFoundException("Ward not found"));

        mockMvc.perform(get("/api/ward/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Ward not found"));
    }

    // Test for updating a ward - positive case
    @Test
    void updateWard_PositiveTest() throws Exception {
        WardRequest request = new WardRequest();
        request.setName("Ward B");
        request.setCapacity(30);
        request.setLocation("Second Floor");

        Ward updatedWard = new Ward();
        updatedWard.setWardId(1L);
        updatedWard.setName("Ward B");
        updatedWard.setCapacity(30);
        updatedWard.setLocation("Second Floor");

        when(wardService.updateWard(anyLong(), any(WardRequest.class))).thenReturn(updatedWard);

        mockMvc.perform(put("/api/ward/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wardId").value(1L))
                .andExpect(jsonPath("$.name").value("Ward B"));
    }

    // Test for updating a ward - negative case (not found)
    @Test
    void updateWard_NegativeTest_NotFound() throws Exception {
        WardRequest request = new WardRequest();
        request.setName("Ward C");

        when(wardService.updateWard(anyLong(), any(WardRequest.class)))
                .thenThrow(new ResourceNotFoundException("Ward not found"));

        mockMvc.perform(put("/api/ward/{id}", 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Ward not found"));
    }

    // Test for deleting a ward - positive case
    @Test
    void deleteWard_PositiveTest() throws Exception {
        mockMvc.perform(delete("/api/ward/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(wardService, times(1)).deleteWard(1L);
    }

    // Test for deleting a ward - negative case (not found)
    @Test
    void deleteWard_NegativeTest_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Ward not found")).when(wardService).deleteWard(100L);

        mockMvc.perform(delete("/api/ward/{id}", 100L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Ward not found"));
    }
}
