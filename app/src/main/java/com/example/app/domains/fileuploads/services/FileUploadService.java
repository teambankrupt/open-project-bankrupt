package com.example.app.domains.fileuploads.services;


import com.example.app.domains.fileuploads.models.entities.UploadProperties;

import com.example.app.exceptions.notfound.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {
    UploadProperties uploadFile(MultipartFile file, String namespace, String uniqueProperty, int scalingHeight) throws IOException;

    UploadProperties find(Long imageId) throws NotFoundException;

    UploadProperties findByUuid(String uuid) throws NotFoundException;
}
