package com.application.tasklest;

import java.io.File;
import java.util.Objects;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import com.application.config.property.BatchConfig;

public class FtpTask implements Tasklet {

	@Autowired
	@Qualifier(value = "logicalName")
	private MessageChannel gateway;
	@Autowired
	private BatchConfig batchConfig;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		String generatedFileName = (String) chunkContext.getStepContext().getJobExecutionContext()
				.get("generatedFileName");
		File generatedFile = null;
		if (Objects.nonNull(generatedFileName)) {
			try {
				generatedFile = new File(batchConfig.getFileOutPutDirectory() + generatedFileName);
				Message<File> message = MessageBuilder.withPayload(generatedFile).build();
				gateway.send(message);
				generatedFile.delete();
			} catch (Exception e) {
				// log
			}
		} else {
			// Log no File to send
		}

		return RepeatStatus.FINISHED;
	}

}
