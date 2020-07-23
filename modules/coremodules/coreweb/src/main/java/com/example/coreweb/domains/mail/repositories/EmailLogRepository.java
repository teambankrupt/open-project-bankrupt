package com.example.coreweb.domains.mail.repositories;

import com.example.coreweb.domains.mail.models.entities.EmailLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {
}
