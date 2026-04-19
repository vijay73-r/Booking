package com.smartbooking.smart_booking.booking.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PricingService {

    public BigDecimal calculateBaseAmount(
            BigDecimal roomPrice,
            long nights,
            int units
    ) {
        return roomPrice
                .multiply(BigDecimal.valueOf(nights))
                .multiply(BigDecimal.valueOf(units));
    }

    public BigDecimal calculateGst(BigDecimal baseAmount) {
        return baseAmount
                .multiply(BigDecimal.valueOf(0.18))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
