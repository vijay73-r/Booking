package com.smartbooking.smart_booking.property.service;

import com.smartbooking.smart_booking.auth.entity.User;
import com.smartbooking.smart_booking.auth.repository.UserRepository;
import com.smartbooking.smart_booking.common.exception.ResourceNotFoundException;
import com.smartbooking.smart_booking.property.dto.CreatePropertyRequest;
import com.smartbooking.smart_booking.property.dto.PropertyResponse;
import com.smartbooking.smart_booking.property.entity.City;
import com.smartbooking.smart_booking.property.entity.Property;
import com.smartbooking.smart_booking.property.repository.CityRepository;
import com.smartbooking.smart_booking.property.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final CityRepository cityRepository;
    private final UserRepository userRepository;

    public PropertyResponse create(
            Long hostUserId,
            CreatePropertyRequest request
    ) {

        User host = userRepository.findById(hostUserId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Host not found"));

        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("City not found"));

        Property property = new Property();
        property.setTitle(request.getTitle());
        property.setDescription(request.getDescription());
        property.setAddressLine(request.getAddressLine());
        property.setHost(host);
        property.setCity(city);

        Property saved = propertyRepository.save(property);

        return map(saved);
    }

    public Page<PropertyResponse> search(
            String city,
            int page,
            int size
    ) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Property> result;

        if (city == null || city.isBlank()) {
            result = propertyRepository.findByActiveTrue(pageable);
        } else {
            result = propertyRepository
                    .findByCity_NameIgnoreCaseAndActiveTrue(city, pageable);
        }

        return result.map(this::map);
    }

    private PropertyResponse map(Property property) {

        return PropertyResponse.builder()
                .id(property.getId())
                .title(property.getTitle())
                .description(property.getDescription())
                .addressLine(property.getAddressLine())
                .city(property.getCity().getName())
                .country(property.getCity().getCountry().getName())
                .hostEmail(property.getHost().getEmail())
                .build();
    }
}
