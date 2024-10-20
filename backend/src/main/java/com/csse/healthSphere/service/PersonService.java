package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.AuthUser;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Person;
import com.csse.healthSphere.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    /**
     * Get the logged in person
     * @param authUser the authenticated user
     * @return the logged in person
     */
    public Person getLoggedPerson(AuthUser authUser){
        return personRepository.findById(authUser.getPerson().getPersonId())
                .orElseThrow(()-> new ResourceNotFoundException("Person not found"));
    }
}
