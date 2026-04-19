package com.smartbooking.smart_booking.notification.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationRequest {
    private String email;
    private String phone;
    private String subject;
    private String message;
}
