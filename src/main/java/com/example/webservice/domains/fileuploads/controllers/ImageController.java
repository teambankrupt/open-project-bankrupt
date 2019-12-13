package com.example.webservice.domains.fileuploads.controllers;

import com.example.webservice.commons.utils.ImageValidator;
import com.example.webservice.config.security.SecurityContext;
import com.example.webservice.domains.fileuploads.models.entities.UploadProperties;
import com.example.webservice.domains.fileuploads.models.responses.ImageUploadResponse;
import com.example.webservice.domains.fileuploads.services.FileUploadService;
import com.example.webservice.domains.users.models.entities.User;
import com.example.webservice.exceptions.invalid.ImageInvalidException;
import com.example.webservice.exceptions.notfound.NotFoundException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final FileUploadService uploadService;

    @Autowired
    public ImageController(FileUploadService uploadService) {
        this.uploadService = uploadService;
    }

    // UPLOAD IMAGES
    @PostMapping("")
    public ResponseEntity uploadImage(@RequestParam("file") MultipartFile file,
                                      @RequestParam(value = "namespace") String namespace) throws ImageInvalidException, IOException {
        ImageUploadResponse response = upload(file, namespace, SecurityContext.getCurrentUser());
        return ResponseEntity.ok(response);
    }

    // UPLOAD IMAGESpost
    @PostMapping("/bulk")
    public ResponseEntity uploadImages(@RequestParam("files") MultipartFile[] files,
                                       @RequestParam(value = "namespace") String namespace) throws ImageInvalidException, IOException {
        if (files.length == 0) return ResponseEntity.badRequest().body("At least one image is expected!");
        List<ImageUploadResponse> responses = new ArrayList<>();
        for (MultipartFile file : files)
            responses.add(upload(file, namespace, SecurityContext.getCurrentUser()));

        return ResponseEntity.ok(responses);
    }


    @GetMapping("/{uuid}")
    private ResponseEntity getImage(@PathVariable("uuid") String uuid) throws NotFoundException, IOException {
        UploadProperties properties = this.uploadService.findByUuid(uuid);
        File file = new File(properties.getFilePath());
        if (!file.exists()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                .body(FileUtils.readFileToByteArray(file));
    }

    private ImageUploadResponse upload(MultipartFile file, String namespace, User currentUser) throws IOException, ImageInvalidException {
        if (!ImageValidator.isImageValid(file))
            throw new ImageInvalidException("Invalid Image");
        ImageUploadResponse response = new ImageUploadResponse();
        UploadProperties uploadProperties = this.uploadService.uploadFile(file, namespace, currentUser.getUsername(), 1200);
        response.setImageUrl("/images/" + uploadProperties.getUuid());
        uploadProperties = this.uploadService.uploadFile(file, namespace, currentUser.getUsername(), 600);
        response.setThumbUrl("/images/" + uploadProperties.getUuid());
        return response;
    }
}
