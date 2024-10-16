package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.AuthUser;
import com.csse.healthSphere.enums.Role;
import com.csse.healthSphere.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final PersonRepository personRepository;


    @Override
    public AuthUser loadUserByUsername(String email) throws UsernameNotFoundException {
        return findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }


    public Optional<AuthUser> findByEmail(String email) {
        return personRepository.findPersonByEmail(email).map(
                person -> new AuthUser(person.getEmail(), person.getPassword(), Role.ADMIN, person)
        );
    }
}
