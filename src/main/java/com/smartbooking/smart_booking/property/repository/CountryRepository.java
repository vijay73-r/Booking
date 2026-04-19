package com.smartbooking.smart_booking.property.repository;

import com.smartbooking.smart_booking.property.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {

}
