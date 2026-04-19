package com.smartbooking.smart_booking.payment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentResponse {

    private Long paymentId;
    private String status;
    private String transactionRef;
}
