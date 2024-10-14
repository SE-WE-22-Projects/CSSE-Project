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
    private Long personId;
    private LocalDate dob;
    private String name;
    private String address;
    private String gender;
    private String email;
    private String phoneNo;
    @JsonIgnore
    private String password;
}
