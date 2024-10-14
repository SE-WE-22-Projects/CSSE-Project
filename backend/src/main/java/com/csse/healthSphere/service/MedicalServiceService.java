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

    public MedicalService createService(MedicalServiceRequest medicalServiceRequest){
        MedicalService medicalService = modelMapper.map(medicalServiceRequest, MedicalService.class);
        return medicalServiceRepository.save(medicalService);
    }

    public List<MedicalService> getAllServices(){
        return medicalServiceRepository.findAll();
    }

    public MedicalService getServiceById(Long id){
        return medicalServiceRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Service not found"));
    }

    public MedicalService updateService(Long id, MedicalServiceRequest medicalServiceRequest){
        MedicalService medicalService = medicalServiceRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Service not found"));
        medicalService.setName(medicalServiceRequest.getName());
        medicalService.setDescription(medicalServiceRequest.getDescription());
        medicalService.setPrice(medicalServiceRequest.getPrice());

        return medicalServiceRepository.save(medicalService);
    }

    public void deleteService(Long id){
        MedicalService medicalService = medicalServiceRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Service not found"));
        medicalServiceRepository.delete(medicalService);
    }
}
