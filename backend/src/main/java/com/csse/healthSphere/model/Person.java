package com.csse.healthSphere.model;

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
    LocalDate date;
    String name;
    String address;
    String email;
    String phoneNo;
    String password;
}
