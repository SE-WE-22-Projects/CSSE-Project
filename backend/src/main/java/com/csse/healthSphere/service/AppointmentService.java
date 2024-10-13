package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.AppointmentRequest;
import com.csse.healthSphere.model.Appointment;
import com.csse.healthSphere.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public Appointment createAppointment(AppointmentRequest appointmentRequest){

        return null;
    }

    public List<Appointment> getAllAppointments(){

        return List.of();
    }

    public Appointment getAppointmentById(Long id){
        return null;
    }

    public Appointment updateAppointment(Long id, AppointmentRequest appointmentRequest){
        return null;
    }

    public void deleteAppointment(Long id){

    }
}
