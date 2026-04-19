package com.smartbooking.smart_booking.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RatingSummaryResponse {
    private Double averageRating;
    private Long totalReviews;
}
