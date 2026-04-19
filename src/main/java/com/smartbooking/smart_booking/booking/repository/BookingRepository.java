package com.smartbooking.smart_booking.booking.repository;

import com.smartbooking.smart_booking.booking.entity.Booking;
import com.smartbooking.smart_booking.booking.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomer_Id(Long userId);

    List<Booking> findByStatus(BookingStatus status);
}
