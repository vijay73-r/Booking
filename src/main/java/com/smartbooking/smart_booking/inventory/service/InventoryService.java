package com.smartbooking.smart_booking.inventory.service;

import com.smartbooking.smart_booking.common.exception.BadRequestException;
import com.smartbooking.smart_booking.common.exception.ResourceNotFoundException;
import com.smartbooking.smart_booking.inventory.dto.AvailabilityResponse;
import com.smartbooking.smart_booking.inventory.dto.CreateRoomRequest;
import com.smartbooking.smart_booking.inventory.entity.Room;
import com.smartbooking.smart_booking.inventory.entity.RoomAvailability;
import com.smartbooking.smart_booking.inventory.repository.RoomAvailabilityRepository;
import com.smartbooking.smart_booking.inventory.repository.RoomRepository;
import com.smartbooking.smart_booking.property.entity.Property;
import com.smartbooking.smart_booking.property.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final RoomRepository roomRepository;
    private final RoomAvailabilityRepository availabilityRepository;
    private final PropertyRepository propertyRepository;

    public Room createRoom(CreateRoomRequest request) {

        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Property not found"));

        Room room = new Room();
        room.setName(request.getName());
        room.setCapacity(request.getCapacity());
        room.setBasePrice(request.getBasePrice());
        room.setTotalUnits(request.getTotalUnits());
        room.setProperty(property);

        return roomRepository.save(room);
    }

    @Transactional
    public void seedAvailability(
            Long roomId,
            LocalDate start,
            LocalDate end
    ) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Room not found"));

        LocalDate date = start;

        while (!date.isAfter(end)) {

            if (availabilityRepository
                    .findByRoom_IdAndAvailableDate(roomId, date)
                    .isEmpty()) {

                RoomAvailability availability =
                        new RoomAvailability();

                availability.setRoom(room);
                availability.setAvailableDate(date);
                availability.setAvailableUnits(room.getTotalUnits());

                availabilityRepository.save(availability);
            }

            date = date.plusDays(1);
        }
    }

    public List<AvailabilityResponse> getAvailability(
            Long roomId,
            LocalDate start,
            LocalDate end
    ) {

        List<RoomAvailability> rows =
                availabilityRepository
                        .findByRoom_IdAndAvailableDateBetween(
                                roomId, start, end);

        return rows.stream()
                .map(this::map)
                .toList();
    }

    @Transactional
    public void reduceStock(
            Long roomId,
            LocalDate date,
            int units
    ) {

        RoomAvailability availability =
                availabilityRepository
                        .findByRoom_IdAndAvailableDate(roomId, date)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Availability not found"));

        if (availability.getAvailableUnits() < units) {
            throw new BadRequestException("Insufficient stock");
        }

        availability.setAvailableUnits(
                availability.getAvailableUnits() - units
        );
    }

    private AvailabilityResponse map(RoomAvailability row) {

        return AvailabilityResponse.builder()
                .roomId(row.getRoom().getId())
                .roomName(row.getRoom().getName())
                .date(row.getAvailableDate())
                .availableUnits(row.getAvailableUnits())
                .build();
    }
}
