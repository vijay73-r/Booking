package com.smartbooking.smart_booking.notification.provider;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TwilioSmsProvider implements SmsProvider {

    @Override
    public void send(
            String phone,
            String message
    ) {

        log.info(
                "Sending sms to={}",
                phone
        );

        /*
        Later:
        Twilio.init(accountSid, authToken);
        Message.creator(...).create();
        */

        log.info("SMS sent to={}", phone);
    }
}
