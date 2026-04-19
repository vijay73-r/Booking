package com.smartbooking.smart_booking.notification.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SmtpEmailProvider implements EmailProvider{
    @Override
    public void send(String to, String subject, String body) {
        log.info(
                "Sending email to={} subject={}",
                to,
                subject
        );

        /*
        Later use JavaMailSender:
        mailSender.send(...)
        */

        log.info("Email sent to={}", to);
    }
}

