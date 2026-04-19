package com.smartbooking.smart_booking.property.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PropertyResponse {
    private Long id;
    private String title;
    private String description;
    private String addressLine;
    private String city;
    private String country;
    private String hostEmail;
}
