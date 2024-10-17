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

    public Ward createWard(WardRequest wardRequest){
        Ward ward = modelMapper.map(wardRequest,Ward.class);
        ward.setWardId(null);
        return wardRepository.save(ward);
    }

    public List<Ward> getAllWards(){
        return wardRepository.findAll();
    }

    public Ward getWardById(Long id){
        return wardRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Ward not found"));
    }

    public Ward updateWard(Long id, WardRequest wardRequest){
        Ward ward = wardRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Ward not found"));
        modelMapper.map(wardRequest,ward);
        return wardRepository.save(ward);
    }

    public void deleteWard(Long id){
        Ward ward = wardRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Ward not found"));
        wardRepository.delete(ward);
    }
}
