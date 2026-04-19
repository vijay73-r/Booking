package com.smartbooking.smart_booking.property.repository;

import com.smartbooking.smart_booking.property.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
