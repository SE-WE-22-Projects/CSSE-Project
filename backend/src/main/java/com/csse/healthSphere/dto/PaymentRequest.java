package com.csse.healthSphere.dto;

import com.csse.healthSphere.enums.PaymentType;
import lombok.Data;

@Data
public class PaymentRequest {
    PaymentType paymentType;
}
