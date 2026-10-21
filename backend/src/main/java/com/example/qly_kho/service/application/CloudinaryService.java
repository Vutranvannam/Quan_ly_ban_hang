package com.example.qly_kho.service.application;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    Map<String, String> uploadImage(MultipartFile file);

    void deleteImage(String publicId);
}
