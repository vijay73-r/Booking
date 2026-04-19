package com.smartbooking.smart_booking.property.controller;

import com.smartbooking.smart_booking.common.response.ApiResponse;
import com.smartbooking.smart_booking.property.dto.CreatePropertyRequest;
import com.smartbooking.smart_booking.property.dto.PropertyResponse;
import com.smartbooking.smart_booking.property.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/properties")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping
    public ResponseEntity<ApiResponse<PropertyResponse>> create(
            @Valid @RequestBody CreatePropertyRequest request
    ) {

        Long hostUserId = 1L; // temporary until JWT principal wired

        PropertyResponse response =
                propertyService.create(hostUserId, request);

        return ResponseEntity.ok(
                ApiResponse.<PropertyResponse>builder()
                        .success(true)
                        .message("Property created")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PropertyResponse>>> search(
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        return ResponseEntity.ok(
                ApiResponse.<Page<PropertyResponse>>builder()
                        .success(true)
                        .message("Properties fetched")
                        .data(propertyService.search(city, page, size))
                        .build()
        );
    }
}
