package com.smartbooking.smart_booking.inventory.controller;

import com.smartbooking.smart_booking.common.response.ApiResponse;
import com.smartbooking.smart_booking.inventory.dto.AvailabilityResponse;
import com.smartbooking.smart_booking.inventory.dto.CreateRoomRequest;
import com.smartbooking.smart_booking.inventory.entity.Room;
import com.smartbooking.smart_booking.inventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping("/rooms")
    public ResponseEntity<ApiResponse<Long>> createRoom(
            @Valid @RequestBody CreateRoomRequest request
    ) {

        Room room = inventoryService.createRoom(request);

        return ResponseEntity.ok(
                ApiResponse.<Long>builder()
                        .success(true)
                        .message("Room created")
                        .data(room.getId())
                        .build()
        );
    }

    @PostMapping("/rooms/{roomId}/seed")
    public ResponseEntity<ApiResponse<Void>> seed(
            @PathVariable Long roomId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate start,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate end
    ) {

        inventoryService.seedAvailability(roomId, start, end);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Availability seeded")
                        .build()
        );
    }

    @GetMapping("/rooms/{roomId}/availability")
    public ResponseEntity<ApiResponse<List<AvailabilityResponse>>> get(
            @PathVariable Long roomId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate start,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate end
    ) {

        return ResponseEntity.ok(
                ApiResponse.<List<AvailabilityResponse>>builder()
                        .success(true)
                        .message("Availability fetched")
                        .data(
                                inventoryService.getAvailability(
                                        roomId, start, end
                                )
                        )
                        .build()
        );
    }
}
