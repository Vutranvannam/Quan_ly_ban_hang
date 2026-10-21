package com.example.qly_kho.service.application.impl;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.qly_kho.exception.ErrorCode;
import com.example.qly_kho.exception.custom.AppException;
import com.example.qly_kho.service.application.CloudinaryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public Map<String, String> uploadImage(MultipartFile file) {
        try {

            if(file == null || file.isEmpty()) {
                throw new AppException(
                    ErrorCode.BAD_REQUEST, 
                    "File is empty"
                );
            }

            if (file.getSize() > 5 * 1024 * 1024) {
                throw new AppException(
                    ErrorCode.BAD_REQUEST,
                    "File must be <= 5MB"
                );
            }

             Map<?, ?> result = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                    "folder", "product",
                    "format", "png",
                    "resource_type", "image",
                    "public_id", "product_" + UUID.randomUUID(),
                    "overwrite", true
                )
            );

            String url = (String) result.get("secure_url");
            String publicId = (String) result.get("public_id");

            return Map.of(
                "url", url, 
                "publicId", publicId
            );

        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(
                ErrorCode.INTERNAL_ERROR,
                "Upload failed: " + e.getMessage()
            );
        }
    }

    @Override
    public void deleteImage(String publicId) {
        try {
            cloudinary.uploader().destroy(
                publicId,
                ObjectUtils.emptyMap()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete image from Cloudinary", e);
        }
    }
}
