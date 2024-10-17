package com.csse.healthSphere.service;

import com.csse.healthSphere.model.Appointment;
import com.csse.healthSphere.model.Diagnosis;
import com.csse.healthSphere.dto.DiagnosisRequest;
import com.csse.healthSphere.model.Doctor;
import com.csse.healthSphere.model.Patient;
import com.csse.healthSphere.repository.AppointmentRepository;
import com.csse.healthSphere.repository.DiagnosisRepository;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.repository.DoctorRepository;
import com.csse.healthSphere.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiagnosisService {
    private final DiagnosisRepository diagnosisRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private ModelMapper modelMapper;

    /**
     * Creates a diagnosis from the given data
     *
     * @param diagnosisRequest the data for the new diagnosis
     * @return the created diagnosis
     */
    public Diagnosis createDiagnosis(DiagnosisRequest diagnosisRequest) {
        Patient patient = patientRepository.findById(diagnosisRequest.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        Doctor doctor = doctorRepository.findById(diagnosisRequest.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        Appointment appointment = appointmentRepository.findById(diagnosisRequest.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        Diagnosis diagnosis = modelMapper.map(diagnosisRequest, Diagnosis.class);
        diagnosis.setDiagnosisId(null);
        diagnosis.setPatient(patient);
        diagnosis.setDoctor(doctor);
        diagnosis.setAppointment(appointment);
        return diagnosisRepository.save(diagnosis);
    }

    /**
     * Gets a list of all existing diagnosis.
     *
     * @return a list of diagnosis
     */
    public List<Diagnosis> getAllDiagnosis() {
        return diagnosisRepository.findAll();
    }

    /**
     * Gets the diagnosis with the given id.
     *
     * @param id the id of the diagnosis
     * @return the diagnosis
     * @throws ResourceNotFoundException if the diagnosis does not exist
     */
    public Diagnosis getDiagnosisById(Long id) {
        return diagnosisRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Diagnosis not found"));
    }

    /**
     * Updates the diagnosis content
     *
     * @param id               the id of the diagnosis
     * @param diagnosisRequest the new data for the diagnosis
     * @return the updated diagnosis
     */
    public Diagnosis updateDiagnosis(Long id, DiagnosisRequest diagnosisRequest) {
        Diagnosis diagnosis = diagnosisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diagnosis not found"));
        diagnosis.setDiagnosis(diagnosisRequest.getDiagnosis());
        diagnosis.setPrescription(diagnosis.getPrescription());

        return diagnosisRepository.save(diagnosis);
    }

    /**
     * Deletes a diagnosis
     *
     * @param id the id of the diagnosis
     */
    public void deleteDiagnosis(Long id) {
        Diagnosis diagnosis = diagnosisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diagnosis not found"));
        diagnosisRepository.delete(diagnosis);
    }

    /**
     *
     * @param appointmentId
     * @return
     */
    public Diagnosis findDiagnosisByAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(()-> new ResourceNotFoundException("Appointment not found"));
        return diagnosisRepository.findByAppointment(appointment);
    }

    /**
     *
     * @param patientId
     * @return
     */
    public List<Diagnosis> findDiagnosisByPatient(Long patientId){
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()-> new ResourceNotFoundException("Patient not found"));
        return diagnosisRepository.findByPatient(patient);
    }
}
