package com.smartbooking.smart_booking.inventory.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateRoomRequest {
    @NotBlank
    private String name;

    @NotNull
    @Min(1)
    private Integer capacity;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal basePrice;

    @NotNull
    @Min(1)
    private Integer totalUnits;

    @NotNull
    private Long propertyId;
}
