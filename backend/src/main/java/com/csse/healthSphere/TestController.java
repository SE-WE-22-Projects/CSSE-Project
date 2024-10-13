package com.csse.healthSphere;

import com.csse.healthSphere.repository.MedicalStaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {
    private final MedicalStaffRepository repository;

    @GetMapping("/")
    public String index() {
        return repository.findAll().toString();
    }

}