package com.syelodge.common.dto;

import com.syelodge.common.enums.UserRole;

import java.util.UUID;

/** Lightweight author card attached to posts and comments. */
public record AuthorDto(
        UUID id,
        String fullName,
        String avatarUrl,
        UserRole role
) {
}
