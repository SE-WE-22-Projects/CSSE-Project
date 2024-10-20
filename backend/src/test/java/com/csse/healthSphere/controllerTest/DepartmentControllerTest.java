package com.csse.healthSphere.controllerTest;

import com.csse.healthSphere.controller.BaseController;
import com.csse.healthSphere.controller.DepartmentController;
import com.csse.healthSphere.dto.DepartmentRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Department;
import com.csse.healthSphere.service.DepartmentService;
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
public class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).setControllerAdvice(new BaseController()).build();
    }

    // Test for creating a department - positive case
    @Test
    void createDepartment_PositiveTest() throws Exception {
        Department department = new Department();
        department.setDepartmentId(1L);
        department.setName("IT Department");

        DepartmentRequest request = new DepartmentRequest();
        request.setName("IT Department");

        when(departmentService.createDepartment(any(DepartmentRequest.class))).thenReturn(department);

        mockMvc.perform(post("/api/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.departmentId").value(department.getDepartmentId()))
                .andExpect(jsonPath("$.name").value(department.getName()));
    }

    // Test for getting all departments - positive case
    @Test
    void getAllDepartments_PositiveTest() throws Exception {
        List<Department> departments = List.of(new Department());
        when(departmentService.getAllDepartments()).thenReturn(departments);

        mockMvc.perform(get("/api/department"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    // Test for getting all departments - negative case (no departments found)
    @Test
    void getAllDepartments_NegativeTest_NoDepartmentsFound() throws Exception {
        when(departmentService.getAllDepartments()).thenReturn(List.of());

        mockMvc.perform(get("/api/department"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // Test for getting department by id - positive case
    @Test
    void getDepartmentById_PositiveTest() throws Exception {
        Department department = new Department();
        department.setDepartmentId(1L);
        department.setName("Finance Department");

        when(departmentService.getDepartmentById(1L)).thenReturn(department);

        mockMvc.perform(get("/api/department/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departmentId").value(1L))
                .andExpect(jsonPath("$.name").value("Finance Department"));
    }

    // Test for getting department by id - negative case (department not found)
    @Test
    void getDepartmentById_NegativeTest_NotFound() throws Exception {
        when(departmentService.getDepartmentById(100L))
                .thenThrow(new ResourceNotFoundException("Department not found"));

        mockMvc.perform(get("/api/department/{id}", 100L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Department not found"));
    }

    // Test for updating a department - positive case
    @Test
    void updateDepartment_PositiveTest() throws Exception {
        Department department = new Department();
        department.setDepartmentId(1L);
        department.setName("HR Department");

        DepartmentRequest request = new DepartmentRequest();
        request.setName("HR Department");

        when(departmentService.updateDepartment(anyLong(), any(DepartmentRequest.class))).thenReturn(department);

        mockMvc.perform(put("/api/department/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departmentId").value(department.getDepartmentId()))
                .andExpect(jsonPath("$.name").value(department.getName()));
    }

    // Test for updating a department - negative case (department not found)
    @Test
    void updateDepartment_NegativeTest_NotFound() throws Exception {
        DepartmentRequest request = new DepartmentRequest();
        request.setName("Marketing Department");

        when(departmentService.updateDepartment(anyLong(), any(DepartmentRequest.class)))
                .thenThrow(new ResourceNotFoundException("Department not found"));

        mockMvc.perform(put("/api/department/{id}", 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Department not found"));
    }

    // Test for deleting a department - positive case
    @Test
    void deleteDepartment_PositiveTest() throws Exception {
        mockMvc.perform(delete("/api/department/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(departmentService, times(1)).deleteDepartment(1L);
    }

    // Test for deleting a department - negative case (department not found)
    @Test
    void deleteDepartment_NegativeTest_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Department not found")).when(departmentService).deleteDepartment(100L);

        mockMvc.perform(delete("/api/department/{id}", 100L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Department not found"));
    }
}
