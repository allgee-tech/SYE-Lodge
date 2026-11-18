package com.syelodge.common.dto;

import com.syelodge.common.enums.AuditAction;

import java.time.Instant;
import java.util.UUID;

public record AuditLogDto(
        UUID id,
        String actorName,
        AuditAction action,
        String targetType,
        String targetId,
        String targetLabel,
        String details,
        Instant createdAt
) {
}
