package com.application.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.application.model.EventModel;
import com.application.reader.JdbcReader;
import com.application.writer.WriterConfig;

@Configuration
public class StepConfiguration {

	@Autowired
	private JdbcReader jdbcReader;
	@Autowired
	private WriterConfig writerConfig;
	@Autowired
	private StepBuilderFactory stepBuilder;

	@Bean
	public Step fileWriteStep() {
		return stepBuilder.get("fileWriteStep").<EventModel, EventModel>chunk(1000).reader(jdbcReader.jdbcEventReader())
				.writer(writerConfig.flatFileWriter()).build();
	}

	@Bean
	public Step sftpStep() {
		return stepBuilder.get("sftpStep").tasklet(writerConfig.sftpTask()).build();
	}
}
