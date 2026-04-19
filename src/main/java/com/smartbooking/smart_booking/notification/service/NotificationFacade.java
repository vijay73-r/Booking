package com.smartbooking.smart_booking.notification.service;

import com.smartbooking.smart_booking.notification.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationFacade {

    private final NotificationService notificationService;

    public void bookingConfirmed(
            String email,
            String phone,
            Long bookingId
    ) {

        notificationService.sendBookingConfirmation(
                NotificationRequest.builder()
                        .email(email)
                        .phone(phone)
                        .subject("Booking Confirmed")
                        .message(
                                "Your booking #" +
                                        bookingId +
                                        " is confirmed"
                        )
                        .build()
        );
    }
}
