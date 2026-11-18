package com.syelodge.common.dto;

import com.syelodge.common.enums.ModerationStatus;

import java.time.Instant;
import java.util.UUID;

public record CommentDto(
        UUID id,
        String body,
        AuthorDto author,
        Instant createdAt,
        ModerationStatus status
) {
}
