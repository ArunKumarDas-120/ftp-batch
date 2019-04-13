package com.application.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class JobScheduleConfig {
	@Autowired
	private JobLauncher lancher;
	
	@Autowired
	@Qualifier(value = "dbJob")
	private Job dbJob;
	
	@Scheduled(cron = "*/10 * * * * *")
	public void runEmployeeJob()
			throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		System.out.println("Sarted Job" + System.currentTimeMillis());

		try {
			lancher.run(dbJob,
					new JobParametersBuilder()
							.addString("SystemTime1",
									String.valueOf(System.currentTimeMillis()))
							.toJobParameters());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
