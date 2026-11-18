package com.syelodge.common.dto;

import com.syelodge.common.enums.ModerationStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/** A student's room-mate / housing request thread. */
public record StudentPostDto(
        UUID id,
        String title,
        String body,
        String preferredLocation,
        BigDecimal budgetMax,
        Integer roommatesNeeded,
        ModerationStatus status,
        String adminNote,
        UUID universityId,
        String universityName,
        AuthorDto author,
        long commentCount,
        boolean saved,
        Instant createdAt,
        Instant updatedAt
) {
}
