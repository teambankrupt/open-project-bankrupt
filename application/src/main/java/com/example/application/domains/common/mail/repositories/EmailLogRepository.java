package com.example.application.domains.common.mail.repositories;

import com.example.application.domains.common.mail.models.entities.EmailLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {
}
