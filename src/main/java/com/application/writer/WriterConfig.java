package com.application.writer;

import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.application.tasklest.FtpTask;

@Configuration
public class WriterConfig {

	
	@Bean
	public FileWriter writer() {
		return new FileWriter();
	}
	
	@Bean
	public Tasklet sftpTask() {
		return new FtpTask();
	}
}
