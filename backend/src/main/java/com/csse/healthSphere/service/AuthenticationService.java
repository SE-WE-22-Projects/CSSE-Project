package com.csse.healthSphere.service;

import com.csse.healthSphere.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final PersonRepository personRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var person = personRepository.findPersonByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        return User.builder().username(person.getEmail()).password(person.getPassword()).roles("USER").build();
    }

    public Optional<UserDetails> findById(Long userId) {
        return personRepository.findById(userId).map(
                person -> new User(person.getEmail(), person.getPassword(), new ArrayList<>())
        );
    }

    public Optional<UserDetails> findByEmail(String email) {
        return personRepository.findPersonByEmail(email).map(
                person -> new User(person.getEmail(), person.getPassword(), new ArrayList<>())
        );
    }
}
