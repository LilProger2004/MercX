package com.diploma.MrcX.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Getter
@Service
public class FileStorageService {

    @Value("${upload.path}")
    private String uploadPath;

    public String uploadFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        Path uploadDir = Paths.get(uploadPath);
        System.out.println("Upload directory (absolute): " + uploadDir.toAbsolutePath());

        if (!Files.exists(uploadDir)) {
            System.out.println("Creating directory: " + uploadDir);
            Files.createDirectories(uploadDir);
        }

        String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadDir.resolve(uniqueFileName);

        System.out.println("Saving to: " + filePath.toAbsolutePath());
        System.out.println("File size: " + file.getSize() + " bytes");

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File successfully saved to: " + filePath.toAbsolutePath());
            return uniqueFileName;
        } catch (IOException e) {
            System.err.println("Failed to save file: " + e.getMessage());
            throw new IOException("Failed to save file: " + e.getMessage(), e);
        }
    }
}
