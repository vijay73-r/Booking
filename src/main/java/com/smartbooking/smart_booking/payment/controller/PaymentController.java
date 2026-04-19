package com.smartbooking.smart_booking.payment.controller;


import com.smartbooking.smart_booking.common.response.ApiResponse;
import com.smartbooking.smart_booking.payment.dto.PaymentRequest;
import com.smartbooking.smart_booking.payment.dto.PaymentResponse;
import com.smartbooking.smart_booking.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> pay(
            @Valid @RequestBody PaymentRequest request
    ) {

        return ResponseEntity.ok(
                ApiResponse.<PaymentResponse>builder()
                        .success(true)
                        .message("Payment successful")
                        .data(
                                paymentService.pay(
                                        request.getBookingId()
                                )
                        )
                        .build()
        );
    }
}
