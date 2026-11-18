package com.syelodge.common.dto;

import com.syelodge.common.enums.UserRole;
import com.syelodge.common.enums.UserStatus;

import java.time.Instant;
import java.util.UUID;

/** Public representation of an account. */
public record UserResponse(
        UUID id,
        String email,
        String fullName,
        String phone,
        UserRole role,
        UserStatus status,
        UniversityDto university,
        String avatarUrl,
        boolean emailVerified,
        boolean adminVerified,
        boolean originalAdmin,
        String businessName,
        String adminNote,
        Instant createdAt
) {
}
