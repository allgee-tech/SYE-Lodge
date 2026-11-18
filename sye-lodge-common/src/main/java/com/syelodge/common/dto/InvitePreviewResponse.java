package com.syelodge.common.dto;

import com.syelodge.common.enums.InvitationStatus;

/** Preview of an administrator invitation, shown before accepting it. */
public record InvitePreviewResponse(
        String email,
        String fullName,
        InvitationStatus status,
        boolean valid,
        String message
) {
}
