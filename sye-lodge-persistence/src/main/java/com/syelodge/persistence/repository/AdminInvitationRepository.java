package com.syelodge.persistence.repository;

import com.syelodge.common.enums.InvitationStatus;
import com.syelodge.persistence.entity.AdminInvitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AdminInvitationRepository extends JpaRepository<AdminInvitation, UUID> {

    Optional<AdminInvitation> findByToken(String token);

    boolean existsByEmailIgnoreCaseAndStatus(String email, InvitationStatus status);

    List<AdminInvitation> findAllByOrderByCreatedAtDesc();
}
