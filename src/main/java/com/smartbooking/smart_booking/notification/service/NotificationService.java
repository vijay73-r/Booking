package com.smartbooking.smart_booking.notification.service;

import com.smartbooking.smart_booking.notification.dto.NotificationRequest;
import com.smartbooking.smart_booking.notification.provider.EmailProvider;
import com.smartbooking.smart_booking.notification.provider.SmsProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmailProvider emailProvider;
    private final SmsProvider smsProvider;

    @Async
    public void sendBookingConfirmation(
            NotificationRequest request
    ) {

        try {

            if (request.getEmail() != null) {
                emailProvider.send(
                        request.getEmail(),
                        request.getSubject(),
                        request.getMessage()
                );
            }

            if (request.getPhone() != null) {
                smsProvider.send(
                        request.getPhone(),
                        request.getMessage()
                );
            }

        } catch (Exception ex) {

            log.error(
                    "Notification failed email={} phone={}",
                    request.getEmail(),
                    request.getPhone(),
                    ex
            );
        }
    }
}
