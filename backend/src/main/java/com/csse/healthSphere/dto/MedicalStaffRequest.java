package com.csse.healthSphere.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MedicalStaffRequest extends PersonRequest {
    Long departmentId;
    Long wardId;
}
