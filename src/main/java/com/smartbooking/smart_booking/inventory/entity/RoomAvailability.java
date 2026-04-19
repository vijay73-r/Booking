package com.smartbooking.smart_booking.inventory.entity;

import com.smartbooking.smart_booking.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(
        name = "room_availability",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"room_id", "available_date"})
        }
)
public class RoomAvailability extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "available_date", nullable = false)
    private LocalDate availableDate;

    @Column(nullable = false)
    private Integer availableUnits;

    @Version
    private Long version;
}
