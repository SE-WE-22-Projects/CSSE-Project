package com.csse.healthSphere.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonRequest {
    LocalDate dob;
    String name;
    String address;
    String email;
    String phoneNo;
    String password;
}
