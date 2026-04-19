package com.smartbooking.smart_booking.payment.service;

import com.smartbooking.smart_booking.booking.entity.Booking;
import com.smartbooking.smart_booking.booking.entity.BookingStatus;
import com.smartbooking.smart_booking.booking.repository.BookingRepository;
import com.smartbooking.smart_booking.common.exception.ResourceNotFoundException;
import com.smartbooking.smart_booking.payment.dto.PaymentResponse;
import com.smartbooking.smart_booking.payment.entity.Payment;
import com.smartbooking.smart_booking.payment.entity.PaymentStatus;
import com.smartbooking.smart_booking.payment.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final InvoiceService invoiceService;

    @Transactional
    public PaymentResponse pay(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found"));

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(booking.getTotalAmount());
        payment.setStatus(PaymentStatus.INITIATED);
        payment.setTransactionRef(
                UUID.randomUUID().toString()
        );

        paymentRepository.save(payment);

        payment.setStatus(PaymentStatus.SUCCESS);
        booking.setStatus(BookingStatus.CONFIRMED);

        invoiceService.generateInvoiceAsync(
                booking.getId()
        );

        return PaymentResponse.builder()
                .paymentId(payment.getId())
                .status(payment.getStatus().name())
                .transactionRef(
                        payment.getTransactionRef()
                )
                .build();
    }
}
