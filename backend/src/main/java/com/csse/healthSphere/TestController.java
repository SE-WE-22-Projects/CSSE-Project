package com.csse.healthSphere;

import com.csse.healthSphere.dto.AppointmentRequest;
import com.csse.healthSphere.model.Appointment;
import com.csse.healthSphere.repository.MedicalStaffRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {
    private final MedicalStaffRepository repository;

    @GetMapping("/")
    public String index() {
        test();
        return repository.findAll().toString();
    }

    private void test() {
        ModelMapper modelMapper = new ModelMapper();
        System.out.println(modelMapper.map(new AppointmentRequest(), Appointment.class));
    }

}
