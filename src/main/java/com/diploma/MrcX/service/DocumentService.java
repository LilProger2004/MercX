package com.diploma.MrcX.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final FileStorageService fileStorageService;


    public Resource loadFileAsResource(String fileName) {
        try {
            Path basePath = Paths.get(fileStorageService.getUploadPath());
            Path filePath = basePath.resolve(fileName).normalize();

            System.out.println("Base path: " + basePath.toAbsolutePath());
            System.out.println("Full file path: " + filePath.toAbsolutePath());

            Resource resource = new UrlResource(filePath.toUri());
            System.out.println("Resource exists: " + resource.exists());

            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + fileName + " at " + filePath);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }
    }
}
