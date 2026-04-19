package com.smartbooking.smart_booking.property.entity;

import com.smartbooking.smart_booking.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cities")
public class City extends BaseEntity {

    @Column(nullable = false, length = 120)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_id")
    private Country country;
}
