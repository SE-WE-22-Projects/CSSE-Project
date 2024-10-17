package com.csse.healthSphere.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wardId;
    private String name;
    private int capacity;
    private String location;
}
