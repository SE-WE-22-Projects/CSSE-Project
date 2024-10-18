package com.csse.healthSphere.model;

import com.csse.healthSphere.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;
    private LocalDate dob;
    private String name;
    private String address;
    private String gender;
    private String email;
    private String phoneNo;
    private Role role;
    @JsonIgnore
    private String password;
}
