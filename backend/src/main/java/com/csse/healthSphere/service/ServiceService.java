package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.ServiceRequest;
import com.csse.healthSphere.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceService {
    private final ServiceRepository serviceRepository;

    public Service createService(ServiceRequest serviceRequest){
        return null;
    }

    public List<Service> getAllServices(){
        return List.of();
    }

    public Service getServiceById(Long id){
        return null;
    }

    public Service updateService(Long id, ServiceRequest serviceRequest){
        return null;
    }

    public void deleteService(Long id){

    }
}
