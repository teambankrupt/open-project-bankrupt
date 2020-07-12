package com.example.acl.domains.fileuploads.repositories;

import com.example.acl.domains.fileuploads.models.entities.UploadProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadRepository extends JpaRepository<UploadProperties, Long> {
    UploadProperties findByUuid(String uuid);
}
