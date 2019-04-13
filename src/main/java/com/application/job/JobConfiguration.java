package com.application.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.application.step.StepConfiguration;

@Configuration
public class JobConfiguration {

	@Autowired
	private JobBuilderFactory jobs;
	@Autowired
	private StepConfiguration step;

	@Bean
	public Job dbJob() {
		return jobs.get("dbJob").incrementer(new RunIdIncrementer()).start(step.fileWriteStep()).next(step.sftpStep())
				.build();
	}
}
