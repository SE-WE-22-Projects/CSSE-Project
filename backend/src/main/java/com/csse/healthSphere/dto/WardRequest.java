package com.csse.healthSphere.dto;

import lombok.Data;

@Data
public class WardRequest {
    private String name;
    private int capacity;
    private String location;
}
