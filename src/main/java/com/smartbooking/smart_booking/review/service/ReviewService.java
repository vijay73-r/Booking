package com.smartbooking.smart_booking.review.service;

import com.smartbooking.smart_booking.auth.entity.User;
import com.smartbooking.smart_booking.auth.repository.UserRepository;
import com.smartbooking.smart_booking.booking.entity.Booking;
import com.smartbooking.smart_booking.booking.entity.BookingStatus;
import com.smartbooking.smart_booking.booking.repository.BookingRepository;
import com.smartbooking.smart_booking.common.exception.ConflictException;
import com.smartbooking.smart_booking.common.exception.ResourceNotFoundException;
import com.smartbooking.smart_booking.review.dto.CreateReviewRequest;
import com.smartbooking.smart_booking.review.dto.RatingSummaryResponse;
import com.smartbooking.smart_booking.review.dto.ReviewResponse;
import com.smartbooking.smart_booking.review.entity.Review;
import com.smartbooking.smart_booking.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public ReviewResponse createReview(
            Long userId,
            CreateReviewRequest request
    ) {

        Booking booking = bookingRepository.findById(
                request.getBookingId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Booking not found"));

        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new ConflictException(
                    "Only confirmed bookings can review");
        }

        if (!booking.getCustomer().getId().equals(userId)) {
            throw new ConflictException(
                    "Invalid booking ownership");
        }

        Long propertyId =
                booking.getRoom()
                        .getProperty()
                        .getId();

        if (reviewRepository.existsByCustomer_IdAndProperty_Id(
                userId, propertyId)) {

            throw new ConflictException(
                    "Review already submitted");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        Review review = new Review();
        review.setCustomer(user);
        review.setBooking(booking);
        review.setProperty(
                booking.getRoom().getProperty()
        );
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review saved = reviewRepository.save(review);

        return map(saved);
    }

    public Page<ReviewResponse> getPropertyReviews(
            Long propertyId,
            int page,
            int size
    ) {

        Pageable pageable =
                PageRequest.of(page, size,
                        Sort.by("id").descending());

        return reviewRepository
                .findByProperty_IdAndVisibleTrue(
                        propertyId,
                        pageable
                )
                .map(this::map);
    }

    public RatingSummaryResponse summary(
            Long propertyId
    ) {

        Double avg =
                reviewRepository
                        .getAverageRating(propertyId);

        Long count =
                reviewRepository
                        .getTotalReviews(propertyId);

        return RatingSummaryResponse.builder()
                .averageRating(avg == null ? 0.0 : avg)
                .totalReviews(count)
                .build();
    }

    private ReviewResponse map(Review review) {

        return ReviewResponse.builder()
                .reviewId(review.getId())
                .userName(
                        review.getCustomer()
                                .getFullName()
                )
                .rating(review.getRating())
                .comment(review.getComment())
                .propertyTitle(
                        review.getProperty()
                                .getTitle()
                )
                .build();
    }
}
