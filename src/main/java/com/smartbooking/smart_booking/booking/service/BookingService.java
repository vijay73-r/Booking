package com.smartbooking.smart_booking.booking.service;

import com.smartbooking.smart_booking.auth.entity.User;
import com.smartbooking.smart_booking.auth.repository.UserRepository;
import com.smartbooking.smart_booking.booking.dto.BookingResponse;
import com.smartbooking.smart_booking.booking.dto.CreateBookingRequest;
import com.smartbooking.smart_booking.booking.entity.Booking;
import com.smartbooking.smart_booking.booking.entity.BookingStatus;
import com.smartbooking.smart_booking.booking.repository.BookingRepository;
import com.smartbooking.smart_booking.common.exception.BadRequestException;
import com.smartbooking.smart_booking.common.exception.ResourceNotFoundException;
import com.smartbooking.smart_booking.inventory.entity.Room;
import com.smartbooking.smart_booking.inventory.repository.RoomRepository;
import com.smartbooking.smart_booking.inventory.service.InventoryService;
import com.smartbooking.smart_booking.notification.service.NotificationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final InventoryService inventoryService;
    private final PricingService pricingService;
    private final NotificationFacade notificationFacade;

    @Transactional
    public BookingResponse createBooking(
            Long userId,
            CreateBookingRequest request
    ) {

        validateDates(
                request.getCheckInDate(),
                request.getCheckOutDate()
        );

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Room not found"));

        LocalDate current = request.getCheckInDate();

        while (current.isBefore(request.getCheckOutDate())) {

            inventoryService.reduceStock(
                    room.getId(),
                    current,
                    request.getUnits()
            );

            current = current.plusDays(1);
        }

        long nights = ChronoUnit.DAYS.between(
                request.getCheckInDate(),
                request.getCheckOutDate()
        );

        BigDecimal baseAmount =
                pricingService.calculateBaseAmount(
                        room.getBasePrice(),
                        nights,
                        request.getUnits()
                );

        BigDecimal gst =
                pricingService.calculateGst(baseAmount);

        BigDecimal total = baseAmount.add(gst);

        Booking booking = new Booking();
        booking.setCustomer(user);
        booking.setRoom(room);
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setUnitsBooked(request.getUnits());
        booking.setBaseAmount(baseAmount);
        booking.setGstAmount(gst);
        booking.setTotalAmount(total);
        booking.setStatus(BookingStatus.PENDING_PAYMENT);

        Booking saved = bookingRepository.save(booking);

        notificationFacade.bookingConfirmed(
                user.getEmail(),
                null,
                saved.getId()
        );

        return BookingResponse.builder()
                .bookingId(saved.getId())
                .status(saved.getStatus().name())
                .baseAmount(saved.getBaseAmount())
                .gstAmount(saved.getGstAmount())
                .totalAmount(saved.getTotalAmount())
                .build();
    }

    private void validateDates(
            LocalDate checkIn,
            LocalDate checkOut
    ) {

        if (!checkOut.isAfter(checkIn)) {
            throw new BadRequestException(
                    "Check-out must be after check-in"
            );
        }

        if (checkIn.isBefore(LocalDate.now())) {
            throw new BadRequestException(
                    "Past check-in not allowed"
            );
        }
    }
}
