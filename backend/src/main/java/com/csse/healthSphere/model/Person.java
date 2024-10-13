package com.csse.healthSphere.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int personId;
    LocalDate dob;
    String name;
    String address;
    String email;
    String phoneNo;

    @JsonIgnore
    String password;
}
