package com.example.qly_kho.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {
    
    @Bean
    public  Cloudinary cloudinary(CloudinaryProperties cloudinaryProperties) {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudinaryProperties.cloudName(),
                "api_key", cloudinaryProperties.apiKey(),
                "api_secret", cloudinaryProperties.apiSecret(),
                "secure", true   
        ));
    }
}
