package com.application.writer;

import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.application.config.property.FtpBatchConfig;
import com.application.model.EventModel;

@Configuration
public class WriterConfig {

	@Autowired
	private FtpBatchConfig ftpBatchConfig;

	private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	private static final DateTimeFormatter fielnameformat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");

	@Bean
	public ItemWriter<EventModel> fileWriter() {

		return new ItemWriter<EventModel>() {

			@Override
			public void write(List<? extends EventModel> items) throws Exception {
				if (Objects.nonNull(items))
					items.stream().forEach(e -> System.out.println(e.toString()));
			}
		};
	}

	@Bean
	public FlatFileItemWriter<EventModel> flatFileWriter() {
		FlatFileItemWriter<EventModel> fileWriter = new FlatFileItemWriter<>();
		fileWriter.setAppendAllowed(true);
		LocalDateTime current = LocalDateTime.now();
		fileWriter.setResource(new FileSystemResource(ftpBatchConfig.getFileOutPutDirectory() 
				+ ftpBatchConfig.getFileName() + "_" + current.format(fielnameformat)+".txt"));
		fileWriter.setLineSeparator(System.lineSeparator());
		fileWriter.setLineAggregator(new DelimitedLineAggregator<EventModel>() {
			{
				setDelimiter("~");
				setFieldExtractor(new BeanWrapperFieldExtractor<EventModel>() {
					{
						setNames(new String[] { "level", "dateAndTime", "source", "eventId", "taskCatagory" });
					}
				});
			}
		});
		// Create Header
		fileWriter.setHeaderCallback(new FlatFileHeaderCallback() {
			LocalDateTime current = LocalDateTime.now();

			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.write("Level Date and Time Source EventId TaskCatagory " + current.format(format));

			}
		});
		// Create Footer
		fileWriter.setFooterCallback(new FlatFileFooterCallback() {
			LocalDateTime current = LocalDateTime.now();

			@Override
			public void writeFooter(Writer writer) throws IOException {
				writer.write("Completed  " + current.format(format));
			}
		});
		return fileWriter;
	}

	@Bean
	public Tasklet sftpTask() {
		
		return new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
}
