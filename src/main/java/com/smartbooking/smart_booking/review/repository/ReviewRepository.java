package com.smartbooking.smart_booking.review.repository;

import com.smartbooking.smart_booking.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByCustomer_IdAndProperty_Id(
            Long userId,
            Long propertyId
    );

    Page<Review> findByProperty_IdAndVisibleTrue(
            Long propertyId,
            Pageable pageable
    );

    @Query("""
        select avg(r.rating)
        from Review r
        where r.property.id = :propertyId
        and r.visible = true
    """)
    Double getAverageRating(Long propertyId);

    @Query("""
        select count(r)
        from Review r
        where r.property.id = :propertyId
        and r.visible = true
    """)
    Long getTotalReviews(Long propertyId);

    Optional<Review> findByIdAndCustomer_Id(
            Long reviewId,
            Long userId
    );
}
