package com.smartbooking.smart_booking.payment.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InvoiceService {


    @Async
    public void generateInvoiceAsync(
            Long bookingId
    ) {

        log.info(
                "Generating invoice for bookingId={}",
                bookingId
        );

        /*
        Later PDF creation logic:
        iText / OpenPDF / Apache PDFBox
        Save to S3 / disk
        */

        log.info(
                "Invoice generated bookingId={}",
                bookingId
        );
    }
}
