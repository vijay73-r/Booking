package com.smartbooking.smart_booking.property.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePropertyRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String addressLine;

    @NotNull
    private Long cityId;
}
