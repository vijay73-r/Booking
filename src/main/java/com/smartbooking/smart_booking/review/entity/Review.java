package com.smartbooking.smart_booking.review.entity;

import com.smartbooking.smart_booking.auth.entity.User;
import com.smartbooking.smart_booking.booking.entity.Booking;
import com.smartbooking.smart_booking.common.entity.BaseEntity;
import com.smartbooking.smart_booking.property.entity.Property;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "reviews",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "property_id"}
                )
        }
)
public class Review extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false, length = 1000)
    private String comment;

    @Column(nullable = false)
    private boolean visible = true;
}
