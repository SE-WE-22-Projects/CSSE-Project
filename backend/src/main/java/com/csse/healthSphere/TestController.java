package com.csse.healthSphere;

import com.csse.healthSphere.repository.MedicalStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private  MedicalStaffRepository repository;

    @GetMapping("/")
    public String index() {
        return repository.findAll().toString();
    }

}