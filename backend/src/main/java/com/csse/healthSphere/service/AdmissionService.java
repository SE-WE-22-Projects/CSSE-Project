package com.csse.healthSphere.service;

import com.csse.healthSphere.model.Admission;
import com.csse.healthSphere.dto.AdmissionRequest;
import com.csse.healthSphere.model.Appointment;
import com.csse.healthSphere.model.Patient;
import com.csse.healthSphere.repository.AdmissionRepository;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.repository.AppointmentRepository;
import com.csse.healthSphere.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdmissionService {
    private final AdmissionRepository admissionRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private ModelMapper modelMapper;

    /**
     * Creates an admission from the given data
     *
     * @param admissionRequest the data for the new admission
     * @return the created admission
     */
    public Admission createAdmission(AdmissionRequest admissionRequest) {
        Admission admission = modelMapper.map(admissionRequest, Admission.class);
        Appointment appointment = appointmentRepository.findById(admissionRequest.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        admission.setAppointment(appointment);
        admission.setAdmissionId(null);
        return admissionRepository.save(admission);
    }

    /**
     * Gets a list of all existing admissions.
     *
     * @return a list of admissions
     */
    public List<Admission> getAllAdmissions() {
        return admissionRepository.findAll();
    }

    /**
     * Gets the admission with the given id.
     *
     * @param id the id of the admission
     * @return the admission
     * @throws ResourceNotFoundException if the admission does not exist
     */
    public Admission getAdmissionById(Long id) {
        return admissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Admission not found"));
    }

    /**
     * Updates the admission content
     *
     * @param id               the id of the admission
     * @param admissionRequest the new data for the admission
     * @return the updated admission
     */
    public Admission updateAdmission(Long id, AdmissionRequest admissionRequest) {
        Admission admission = admissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admission not found"));
        admission.setAdmissionDate(admissionRequest.getAdmissionDate());
        admission.setDescription(admissionRequest.getDescription());

        return admissionRepository.save(admission);
    }

    /**
     * Deletes a admission
     *
     * @param id the id of the admission
     */
    public void deleteAdmission(Long id) {
        Admission admission = admissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admission not found"));
        admissionRepository.delete(admission);
    }

    /**
     * @param appointmentId
     * @return
     */
    public Admission findAdmissionByAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        return admissionRepository.findByAppointment(appointment);
    }

    /**
     *
     * @param patientId
     * @return
     */
    public List<Admission> findAdmissionsByPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        return admissionRepository.findByAppointmentPatient(patient);
    }
}
