package com.smartbooking.smart_booking.booking.controller;

import com.smartbooking.smart_booking.booking.dto.BookingResponse;
import com.smartbooking.smart_booking.booking.dto.CreateBookingRequest;
import com.smartbooking.smart_booking.booking.service.BookingService;
import com.smartbooking.smart_booking.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookingResponse>> create(
            @Valid @RequestBody CreateBookingRequest request
    ) {

        Long userId = 1L; // temporary until JWT principal wired

        BookingResponse response =
                bookingService.createBooking(userId, request);

        return ResponseEntity.ok(
                ApiResponse.<BookingResponse>builder()
                        .success(true)
                        .message("Booking created")
                        .data(response)
                        .build()
        );
    }
}
