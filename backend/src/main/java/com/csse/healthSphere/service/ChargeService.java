package com.csse.healthSphere.service;

import com.csse.healthSphere.model.Charge;
import com.csse.healthSphere.dto.ChargeRequest;
import com.csse.healthSphere.repository.ChargeRepository;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChargeService {
    private final ChargeRepository chargeRepository;
    private ModelMapper modelMapper;

    /**
     * Creates a charge from the given data
     *
     * @param chargeRequest the data for the new charge
     * @return the created charge
     */
    public Charge createCharge(ChargeRequest chargeRequest) {
        Charge charge = modelMapper.map(chargeRequest, Charge.class);
        charge.setChargeId(null);
        return chargeRepository.save(charge);
    }

    /**
     * Gets a list of all existing charges.
     *
     * @return a list of charges
     */
    public List<Charge> getAllCharges() {
        return chargeRepository.findAll();
    }

    /**
     * Gets the charge with the given id.
     *
     * @param id the id of the charge
     * @return the charge
     * @throws ResourceNotFoundException if the charge does not exist
     */
    public Charge getChargeById(Long id) {
        return chargeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Charge not found"));
    }

    /**
     * Updates the charge content
     *
     * @param id            the id of the charge
     * @param chargeRequest the new data for the charge
     * @return the updated charge
     */
    public Charge updateCharge(Long id, ChargeRequest chargeRequest) {
        Charge charge = chargeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Charge not found"));
        charge.setAmount(chargeRequest.getAmount());
        return chargeRepository.save(charge);
    }

    /**
     * Deletes a charge
     *
     * @param id the id of the charge
     */
    public void deleteCharge(Long id) {
        Charge charge = chargeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Charge not found"));
        chargeRepository.delete(charge);
    }
}
