package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.WardRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Ward;
import com.csse.healthSphere.repository.WardRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WardService {
    private final WardRepository wardRepository;
    private final ModelMapper modelMapper;

    /**
     * Create a new ward
     * @param wardRequest
     * @return the newly created ward
     */
    public Ward createWard(WardRequest wardRequest){
        Ward ward = modelMapper.map(wardRequest,Ward.class);
        ward.setWardId(null);
        return wardRepository.save(ward);
    }

    /**
     * Get all wards
     * @return a list of all wards
     */
    public List<Ward> getAllWards(){
        return wardRepository.findAll();
    }

    /**
     * Get a ward by id
     * @param id
     * @return the ward for the given id
     */
    public Ward getWardById(Long id){
        return wardRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Ward not found"));
    }

    /**
     * Update a ward
     * @param id
     * @param wardRequest
     * @return the updated ward
     */
    public Ward updateWard(Long id, WardRequest wardRequest){
        Ward ward = wardRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Ward not found"));
        modelMapper.map(wardRequest,ward);
        return wardRepository.save(ward);
    }

    /**
     * Delete a ward
     * @param id
     */
    public void deleteWard(Long id){
        Ward ward = wardRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Ward not found"));
        wardRepository.delete(ward);
    }
}
