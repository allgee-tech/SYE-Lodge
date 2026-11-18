package com.syelodge.web.security;

import com.syelodge.common.exception.UnauthorizedException;
import com.syelodge.persistence.entity.User;
import org.springframework.security.core.Authentication;

/** Helpers for reading the signed-in account from the security context. */
public final class AuthSupport {

    private AuthSupport() {
    }

    /** Returns the signed-in account, or {@code null} for anonymous requests. */
    public static User current(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        return principal instanceof User user ? user : null;
    }

    /** Returns the signed-in account or throws a 401 response. */
    public static User require(Authentication authentication) {
        User user = current(authentication);
        if (user == null) {
            throw new UnauthorizedException("Please sign in to continue.");
        }
        return user;
    }
}
