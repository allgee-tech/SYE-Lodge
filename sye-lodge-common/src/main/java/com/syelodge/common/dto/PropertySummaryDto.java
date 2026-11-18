package com.syelodge.common.dto;

import com.syelodge.common.enums.FurnishedStatus;
import com.syelodge.common.enums.ListingStatus;
import com.syelodge.common.enums.PropertyType;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/** Compact property representation for cards and lists. */
public record PropertySummaryDto(
        UUID id,
        String title,
        PropertyType propertyType,
        BigDecimal rentAmount,
        FurnishedStatus furnished,
        Integer bedrooms,
        Integer bathrooms,
        Double distanceToCampusKm,
        String city,
        String universityName,
        String coverImageUrl,
        LocalDate moveInDate,
        ListingStatus status,
        Instant createdAt
) {
}
