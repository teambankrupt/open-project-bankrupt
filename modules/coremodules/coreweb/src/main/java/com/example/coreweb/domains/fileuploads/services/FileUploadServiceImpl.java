package com.example.coreweb.domains.fileuploads.services;

import com.example.coreweb.domains.fileuploads.models.entities.UploadProperties;
import com.example.coreweb.domains.fileuploads.repositories.FileUploadRepository;
import com.example.common.exceptions.notfound.NotFoundException;
import com.example.coreweb.utils.FileIO;
import com.example.common.utils.ImageUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${storage.path}")
    private String rootPath;

    @Value("${applicationName}")
    private String waterMarkText;

    private final FileUploadRepository fileUploadRepo;

    @Autowired
    public FileUploadServiceImpl(FileUploadRepository fileUploadRepo) {
        this.fileUploadRepo = fileUploadRepo;
    }

    @Override
    public UploadProperties uploadFile(MultipartFile multipartFile, String namespace, String uniqueProperty, int height) throws IOException {
        byte[] bytes = multipartFile.getBytes();
        String ext = "png";
        if (height > 0) {
            ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            bytes = FileIO.scaleImage(bytes, ext, height);
        }

        System.out.println("EXT: " + ext);

        UploadProperties uploadProperties = new UploadProperties(rootPath, namespace, uniqueProperty, ext);

        File directory = new File(uploadProperties.getDirPath());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File imageFile = File.createTempFile("upload_", "." + ext, directory);
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(imageFile));
        outputStream.write(bytes);
        outputStream.close();

        if ("img".equalsIgnoreCase(ext) || "png".equalsIgnoreCase(ext)) {
            if (UploadProperties.NameSpaces.PRODUCTS.getValue().equalsIgnoreCase(namespace))
                this.applyWatermark(imageFile, this.waterMarkText);
        }

        uploadProperties.setFileName(imageFile.getName());
        uploadProperties = this.fileUploadRepo.save(uploadProperties);
        return uploadProperties;
    }

    @Override
    public UploadProperties find(Long imageId) throws NotFoundException {
        UploadProperties uploadProperties = this.fileUploadRepo.findById(imageId).orElse(null);
        if (uploadProperties == null) throw new NotFoundException("Could not find Image with id: " + imageId);
        return uploadProperties;
    }

    @Override
    public UploadProperties findByUuid(String uuid) throws NotFoundException {
        UploadProperties uploadProperties = this.fileUploadRepo.findByUuid(uuid);
        if (uploadProperties == null) throw new NotFoundException("Could not find requested image!");
        return uploadProperties;
    }

    private void applyWatermark(File imageFile, String text) {
        File output = new File(imageFile.getAbsolutePath());
        try {
            ImageUtils.addTextWatermark(text, "png", imageFile, output);
        } catch (IOException e) {
            System.out.println("Could not watermark image: " + e.getMessage());
        }
    }
}
