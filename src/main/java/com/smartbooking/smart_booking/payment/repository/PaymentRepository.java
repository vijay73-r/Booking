package com.smartbooking.smart_booking.payment.repository;

import com.smartbooking.smart_booking.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository  extends JpaRepository<Payment, Long> {
}
