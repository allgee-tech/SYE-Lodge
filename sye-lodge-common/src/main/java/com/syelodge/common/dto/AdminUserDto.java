package com.syelodge.common.dto;

import com.syelodge.common.enums.UserRole;
import com.syelodge.common.enums.UserStatus;

import java.time.Instant;
import java.util.UUID;

/** Account row shown in the administrator user management screen. */
public record AdminUserDto(
        UUID id,
        String fullName,
        String email,
        String phone,
        UserRole role,
        UserStatus status,
        boolean emailVerified,
        boolean adminVerified,
        boolean originalAdmin,
        String universityName,
        String businessName,
        String adminNote,
        Instant createdAt
) {
}
