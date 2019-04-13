package com.application;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.application.config.property.FtpBatchConfig;

@SpringBootApplication
@EnableConfigurationProperties(FtpBatchConfig.class)
@EnableBatchProcessing
@EnableScheduling
public class FtpBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(FtpBatchApplication.class, args);
	}

}
