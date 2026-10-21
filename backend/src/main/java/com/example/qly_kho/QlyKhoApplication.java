package com.example.qly_kho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

import com.example.qly_kho.config.CloudinaryProperties;
import com.example.qly_kho.config.JwtProperties;

@EnableAsync
@EnableCaching
@SpringBootApplication
@EnableConfigurationProperties({
		JwtProperties.class,
		CloudinaryProperties.class
})
public class QlyKhoApplication {

	public static void main(String[] args) {
		SpringApplication.run(QlyKhoApplication.class, args);
	}

}
