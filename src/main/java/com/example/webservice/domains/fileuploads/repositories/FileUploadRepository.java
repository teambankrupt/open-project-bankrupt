package com.example.webservice.domains.fileuploads.repositories;

import com.example.webservice.domains.fileuploads.models.entities.UploadProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadRepository extends JpaRepository<UploadProperties, Long> {
    UploadProperties findByUuid(String uuid);
}
