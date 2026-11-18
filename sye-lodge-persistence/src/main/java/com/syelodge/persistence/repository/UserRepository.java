package com.syelodge.persistence.repository;

import com.syelodge.common.enums.UserRole;
import com.syelodge.common.enums.UserStatus;
import com.syelodge.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    long countByRole(UserRole role);

    long countByRoleAndStatus(UserRole role, UserStatus status);

    long countByStatus(UserStatus status);

    long countByRoleAndOriginalAdminTrue(UserRole role);

    long countByRoleAndStatusAndAdminVerifiedAtIsNotNull(UserRole role, UserStatus status);

    long countByCreatedAtAfter(Instant since);
}
