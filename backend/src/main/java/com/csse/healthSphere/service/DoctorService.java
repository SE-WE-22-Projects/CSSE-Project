package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.DoctorRequest;
import com.csse.healthSphere.model.Doctor;
import com.csse.healthSphere.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public Doctor createDoctor(DoctorRequest doctorRequest){
        return null;
    }

    public List<Doctor> getAllDoctors(){
        return List.of();
    }

    public Doctor getDoctorById(Long id){
        return null;
    }
    public Doctor updateDoctor(Long id, DoctorRequest doctorRequest){
        return null;
    }
    public void deleteDoctor(Long id){

    }
}
