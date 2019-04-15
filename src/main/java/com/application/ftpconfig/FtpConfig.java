package com.application.ftpconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.application.config.property.FtpConfigProperty;

@Configuration
public class FtpConfig {

	@Autowired
	private FtpConfigProperty ftpProperty;
}
