package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.WardRequest;
import com.csse.healthSphere.model.Ward;
import com.csse.healthSphere.repository.WardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WardService {
    private final WardRepository wardRepository;

    public Ward createWard(WardRequest wardRequest){
        return null;
    }

    public List<Ward> getAllWards(){
        return List.of();
    }

    public Ward getWardById(Long id){
        return null;
    }

    public Ward updateWard(Long id, WardRequest wardRequest){
        return null;
    }

    public void deleteWard(Long id){

    }
}
