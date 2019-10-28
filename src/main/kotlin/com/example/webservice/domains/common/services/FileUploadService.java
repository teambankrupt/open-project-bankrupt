package com.example.webservice.domains.common.services;

import com.example.webservice.domains.common.models.entities.pojo.UploadProperties;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {
    UploadProperties uploadFile(MultipartFile file, String namespace, String uniqueProperty, boolean scaled) throws IOException;
}
