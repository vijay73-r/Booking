package com.smartbooking.smart_booking.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponse {

    private Long reviewId;
    private String userName;
    private Integer rating;
    private String comment;
    private String propertyTitle;

}
