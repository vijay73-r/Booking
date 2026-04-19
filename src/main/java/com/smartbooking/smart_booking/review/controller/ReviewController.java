package com.smartbooking.smart_booking.review.controller;

import com.smartbooking.smart_booking.common.response.ApiResponse;
import com.smartbooking.smart_booking.review.dto.CreateReviewRequest;
import com.smartbooking.smart_booking.review.dto.RatingSummaryResponse;
import com.smartbooking.smart_booking.review.dto.ReviewResponse;
import com.smartbooking.smart_booking.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResponse>> create(
            @Valid @RequestBody CreateReviewRequest request
    ) {

        Long userId = 1L; // temporary JWT principal later

        return ResponseEntity.ok(
                ApiResponse.<ReviewResponse>builder()
                        .success(true)
                        .message("Review submitted")
                        .data(
                                reviewService.createReview(
                                        userId,
                                        request
                                )
                        )
                        .build()
        );
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<ApiResponse<Page<ReviewResponse>>> list(
            @PathVariable Long propertyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        return ResponseEntity.ok(
                ApiResponse.<Page<ReviewResponse>>builder()
                        .success(true)
                        .message("Reviews fetched")
                        .data(
                                reviewService
                                        .getPropertyReviews(
                                                propertyId,
                                                page,
                                                size
                                        )
                        )
                        .build()
        );
    }

    @GetMapping("/property/{propertyId}/summary")
    public ResponseEntity<ApiResponse<RatingSummaryResponse>> summary(
            @PathVariable Long propertyId
    ) {

        return ResponseEntity.ok(
                ApiResponse.<RatingSummaryResponse>builder()
                        .success(true)
                        .message("Rating summary fetched")
                        .data(
                                reviewService.summary(
                                        propertyId
                                )
                        )
                        .build()
        );
    }
}
