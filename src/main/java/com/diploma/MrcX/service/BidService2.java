package com.diploma.MrcX.service;

import com.diploma.MrcX.model.entity.CommentFiles;
import com.diploma.MrcX.model.entity.Files;
import com.diploma.MrcX.model.entity.Order;
import com.diploma.MrcX.reposirtory.CommentFilesRepository;
import com.diploma.MrcX.reposirtory.FilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BidService2 {
    private final FileStorageService fileStorageService;
    private final OrderService orderService;
    private final CommentFilesRepository commentFilesRepository;

    public void placeBid(Order order, List<MultipartFile> documents) {
        if (documents == null || documents.isEmpty() || documents.stream().anyMatch(MultipartFile::isEmpty)) {
            throw new RuntimeException("Необходимо прикрепить хотя бы один документ");
        }
        CommentFiles doc = new CommentFiles();
        documents.forEach(file -> {
            try {
                if (!file.isEmpty()) {
                    doc.setName(file.getOriginalFilename());
                    doc.setOrder(order);
                    doc.setFilePath(fileStorageService.uploadFile(file));
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload file: " + file.getOriginalFilename(), e);
            }
        });

        commentFilesRepository.save(doc);
    }
}
