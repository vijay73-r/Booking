package com.smartbooking.smart_booking.inventory.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class AvailabilityResponse {
    private Long roomId;
    private String roomName;
    private LocalDate date;
    private Integer availableUnits;
}
