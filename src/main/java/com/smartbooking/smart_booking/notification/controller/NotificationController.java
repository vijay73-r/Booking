package com.smartbooking.smart_booking.notification.controller;

import com.smartbooking.smart_booking.common.response.ApiResponse;
import com.smartbooking.smart_booking.notification.service.NotificationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationFacade facade;

    @PostMapping("/test")
    public ResponseEntity<ApiResponse<Void>> test() {

        facade.bookingConfirmed(
                "test@mail.com",
                "9999999999",
                1001L
        );

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Triggered")
                        .build()
        );
    }
}
