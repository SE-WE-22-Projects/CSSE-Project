package com.csse.healthSphere.repository;

import com.csse.healthSphere.model.Patient;
import com.csse.healthSphere.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findPersonByEmail(String email);
}
