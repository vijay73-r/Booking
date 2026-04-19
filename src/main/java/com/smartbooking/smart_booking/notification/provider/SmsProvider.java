package com.smartbooking.smart_booking.notification.provider;

public interface SmsProvider {
    void send(
            String phone,
            String message
    );
}
