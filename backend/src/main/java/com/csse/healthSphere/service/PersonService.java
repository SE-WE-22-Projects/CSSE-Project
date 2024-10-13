package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.PersonRequest;
import com.csse.healthSphere.model.Person;
import com.csse.healthSphere.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public Person createPerson(PersonRequest personRequest){
        return null;
    }

    public List<Person> getAllPersons(){
        return List.of();
    }

    public Person getPersonById(Long id){
        return null;
    }

    public Person updatePerson(Long id, PersonRequest personRequest){
        return null;
    }

    public void deletePerson(Long id){

    }
}
