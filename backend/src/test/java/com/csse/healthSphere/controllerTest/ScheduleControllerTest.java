package com.csse.healthSphere.controllerTest;

import com.csse.healthSphere.controller.BaseController;
import com.csse.healthSphere.controller.ScheduleController;
import com.csse.healthSphere.dto.ScheduleRequest;
import com.csse.healthSphere.enums.WeekDay;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Schedule;
import com.csse.healthSphere.service.ScheduleService;
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

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleControllerTest {

    @Mock
    private ScheduleService scheduleService;

    @InjectMocks
    private ScheduleController scheduleController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(scheduleController).setControllerAdvice(new BaseController()).build();
    }

    // Test for creating a schedule - positive case
    @Test
    void createSchedule_PositiveTest() throws Exception {
        ScheduleRequest request = new ScheduleRequest();
        request.setName("Morning Shift");
        request.setDescription("Doctor's morning schedule");
//        request.setStartTime(LocalTime.of(8, 0));
//        request.setEndTime(LocalTime.of(12, 0));
        request.setDay(WeekDay.MONDAY);
        request.setDoctorId(1L);

        Schedule createdSchedule = new Schedule();
        createdSchedule.setScheduleId(1L);
        createdSchedule.setName("Morning Shift");

        when(scheduleService.createSchedule(any(ScheduleRequest.class))).thenReturn(createdSchedule);

        mockMvc.perform(post("/api/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    // Test for getting all schedules - positive case
    @Test
    void getAllSchedules_PositiveTest() throws Exception {
        List<Schedule> scheduleList = List.of(new Schedule());

        when(scheduleService.getAllSchedules()).thenReturn(scheduleList);

        mockMvc.perform(get("/api/schedule"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    // Test for getting all schedules - negative case (empty list)
    @Test
    void getAllSchedules_NegativeTest_EmptyList() throws Exception {
        when(scheduleService.getAllSchedules()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/schedule"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // Test for getting a schedule by id - positive case
    @Test
    void getScheduleById_PositiveTest() throws Exception {
        Schedule schedule = new Schedule();
        schedule.setScheduleId(1L);
        schedule.setName("Morning Shift");

        when(scheduleService.getScheduleById(1L)).thenReturn(schedule);

        mockMvc.perform(get("/api/schedule/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.scheduleId").value(1L))
                .andExpect(jsonPath("$.name").value("Morning Shift"));
    }

    // Test for getting a schedule by id - negative case (not found)
    @Test
    void getScheduleById_NegativeTest_NotFound() throws Exception {
        when(scheduleService.getScheduleById(1L)).thenThrow(new ResourceNotFoundException("Schedule not found"));

        mockMvc.perform(get("/api/schedule/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Schedule not found"));
    }

    // Test for updating a schedule - positive case
    @Test
    void updateSchedule_PositiveTest() throws Exception {
        ScheduleRequest request = new ScheduleRequest();
        request.setName("Evening Shift");
        request.setDescription("Doctor's evening schedule");
//        request.setStartTime(LocalTime.of(16, 0));
//        request.setEndTime(LocalTime.of(20, 0));
        request.setDay(WeekDay.MONDAY);
        request.setDoctorId(1L);

        Schedule updatedSchedule = new Schedule();
        updatedSchedule.setScheduleId(1L);
        updatedSchedule.setName("Evening Shift");

        when(scheduleService.updateSchedule(anyLong(), any(ScheduleRequest.class))).thenReturn(updatedSchedule);

        mockMvc.perform(put("/api/schedule/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    // Test for updating a schedule - negative case (not found)
    @Test
    void updateSchedule_NegativeTest_NotFound() throws Exception {
        ScheduleRequest request = new ScheduleRequest();
        request.setName("Some Schedule");

        when(scheduleService.updateSchedule(anyLong(), any(ScheduleRequest.class)))
                .thenThrow(new ResourceNotFoundException("Schedule not found"));

        mockMvc.perform(put("/api/schedule/{id}", 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Schedule not found"));
    }

    // Test for deleting a schedule - positive case
    @Test
    void deleteSchedule_PositiveTest() throws Exception {
        mockMvc.perform(delete("/api/schedule/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(scheduleService, times(1)).deleteSchedule(1L);
    }

    // Test for deleting a schedule - negative case (not found)
    @Test
    void deleteSchedule_NegativeTest_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Schedule not found")).when(scheduleService).deleteSchedule(100L);

        mockMvc.perform(delete("/api/schedule/{id}", 100L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Schedule not found"));
    }

    // Test for getting schedules by doctor id - positive case
    @Test
    void getScheduleByDoctor_PositiveTest() throws Exception {
        List<Schedule> scheduleList = List.of(new Schedule());

        when(scheduleService.findSchedulesByDoctor(1L)).thenReturn(scheduleList);

        mockMvc.perform(get("/api/schedule/doctor/{doctorId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    // Test for getting schedules by doctor id - negative case (not found)
    @Test
    void getScheduleByDoctor_NegativeTest_NotFound() throws Exception {
        when(scheduleService.findSchedulesByDoctor(1L)).thenThrow(new ResourceNotFoundException("No schedules found for doctor"));

        mockMvc.perform(get("/api/schedule/doctor/{doctorId}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No schedules found for doctor"));
    }
}
