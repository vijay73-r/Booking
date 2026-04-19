package com.smartbooking.smart_booking.inventory.repository;

import com.smartbooking.smart_booking.inventory.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
