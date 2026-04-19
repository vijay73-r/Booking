package com.smartbooking.smart_booking.notification.provider;

public interface EmailProvider {
    void send(
            String to,
            String subject,
            String body
    );
}
