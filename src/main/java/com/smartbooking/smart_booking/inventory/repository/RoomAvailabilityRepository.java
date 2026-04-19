package com.smartbooking.smart_booking.inventory.repository;

import com.smartbooking.smart_booking.inventory.entity.RoomAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomAvailabilityRepository extends JpaRepository<RoomAvailability, Long> {
    Optional<RoomAvailability> findByRoom_IdAndAvailableDate(
            Long roomId,
            LocalDate date
    );

    List<RoomAvailability> findByRoom_IdAndAvailableDateBetween(
            Long roomId,
            LocalDate start,
            LocalDate end
    );
}
