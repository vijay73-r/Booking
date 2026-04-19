package com.smartbooking.smart_booking.inventory.entity;

import com.smartbooking.smart_booking.common.entity.BaseEntity;
import com.smartbooking.smart_booking.property.entity.Property;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "rooms")
public class Room extends BaseEntity {
    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal basePrice;

    @Column(nullable = false)
    private Integer totalUnits;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(nullable = false)
    private boolean active = true;
}
