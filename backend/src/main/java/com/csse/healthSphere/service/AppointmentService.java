package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.AppointmentRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Appointment;
import com.csse.healthSphere.model.Patient;
import com.csse.healthSphere.repository.AppointmentRepository;
import com.csse.healthSphere.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;
    private final PatientRepository patientRepository;

    public Appointment createAppointment(AppointmentRequest appointmentRequest){
        Appointment appointment =modelMapper.map(appointmentRequest, Appointment.class);
        // fetch patient & check existence
        Patient patient = patientRepository.findById(appointmentRequest.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));;

        appointment.setPatient(patient);
        appointment.setStatus("pending");
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long id){
        return appointmentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Appointment not found"));
    }

    public Appointment updateAppointment(Long id, AppointmentRequest appointmentRequest){
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Appointment not found"));
        appointment.setTime(appointmentRequest.getTime());
        appointment.setDate(appointmentRequest.getDate());
        appointment.setQueueNo(appointmentRequest.getQueueNo());

        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Long id){
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Appointment not found"));
        appointmentRepository.delete(appointment);
    }
}
