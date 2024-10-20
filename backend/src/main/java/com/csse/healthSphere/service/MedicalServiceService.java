package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.MedicalServiceRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.repository.MedicalServiceRepository;
import lombok.RequiredArgsConstructor;
import com.csse.healthSphere.model.MedicalService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalServiceService {
    private final MedicalServiceRepository medicalServiceRepository;
    private final ModelMapper modelMapper;

    /**
     * Creates a medical service from the given data
     *
     * @param medicalServiceRequest the data for the new medical service
     * @return the created medical service
     */
    public MedicalService createService(MedicalServiceRequest medicalServiceRequest) {
        MedicalService medicalService = modelMapper.map(medicalServiceRequest, MedicalService.class);
        medicalService.setServiceId(null);
        return medicalServiceRepository.save(medicalService);
    }

    /**
     * Gets a list of all existing medical services.
     *
     * @return a list of medical services
     */
    public List<MedicalService> getAllServices() {
        return medicalServiceRepository.findAll();
    }

    /**
     * Gets the medical service with the given id.
     *
     * @param id the id of the medical service
     * @return the medical service
     * @throws ResourceNotFoundException if the medical service does not exist
     */
    public MedicalService getServiceById(Long id) {
        return medicalServiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service not found"));
    }

    /**
     * Updates the medical service content
     *
     * @param id                   the id of the medical service
     * @param medicalServiceRequest the new data for the medical service
     * @return the updated medical service
     */
    public MedicalService updateService(Long id, MedicalServiceRequest medicalServiceRequest) {
        MedicalService medicalService = medicalServiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service not found"));
        medicalService.setName(medicalServiceRequest.getName());
        medicalService.setDescription(medicalServiceRequest.getDescription());
        medicalService.setPrice(medicalServiceRequest.getPrice());

        return medicalServiceRepository.save(medicalService);
    }

    /**
     * Deletes a medical service
     *
     * @param id the id of the medical service
     */
    public void deleteService(Long id) {
        MedicalService medicalService = medicalServiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service not found"));
        medicalServiceRepository.delete(medicalService);
    }
}
