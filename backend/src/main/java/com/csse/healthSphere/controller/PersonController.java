package com.csse.healthSphere.controller;

import com.csse.healthSphere.dto.AuthUser;
import com.csse.healthSphere.model.Person;
import com.csse.healthSphere.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequestMapping("/api/person")
@RestController
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    /**
     * Get the logged in person
     * @param principal the principal of the logged in user
     * @return the logged in person
     */
    @GetMapping
    public ResponseEntity<Person> getLoggedPerson(
            Principal principal
    ) {
        var authUser = (AuthUser)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        Person person = personService.getLoggedPerson(authUser);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }
}
