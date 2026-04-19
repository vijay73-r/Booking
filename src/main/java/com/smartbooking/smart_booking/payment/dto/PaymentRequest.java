package com.smartbooking.smart_booking.payment.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {


    @NotNull
    private Long bookingId;
}
