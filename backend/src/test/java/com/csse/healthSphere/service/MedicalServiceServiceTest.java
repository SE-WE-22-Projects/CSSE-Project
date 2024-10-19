package com.csse.healthSphere.service;

import com.csse.healthSphere.model.MedicalService;
import com.csse.healthSphere.repository.MedicalServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MedicalServiceServiceTest {

    @Mock
    private MedicalServiceRepository medicalServiceRepository; // Mock the repository

    @InjectMocks
    private MedicalServiceService medicalServiceService; // Inject the mock into the service

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void getAllMedicalServicesTest() {
        // list of mock MedicalService objects
        List<MedicalService> mockServices = Arrays.asList(
                new MedicalService(1L, "Service1", "Service1 Description", 100),
                new MedicalService(2L, "Service2", "Service2 Description", 200),
                new MedicalService(3L, "Service3", "Service3 Description", 300)
        );

        // Mock the repository's findAll method to return the mock data
        when(medicalServiceRepository.findAll()).thenReturn(mockServices);

        // Call the method under test
        List<MedicalService> result = medicalServiceService.getAllServices(); // Use correct method name

        // Verify the result size and contents
        assertEquals(3, result.size());  // Assert that 3 services are returned
        assertEquals("Service1", result.get(0).getName());  // Check the name of the first service
        assertEquals("Service2", result.get(1).getName());  // Check the name of the second service
        assertEquals("Service3", result.get(2).getName());  // Check the name of the third service

        // Verify that the repository's findAll method was called exactly once
        verify(medicalServiceRepository, times(1)).findAll();
    }
}
