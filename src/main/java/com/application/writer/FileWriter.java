package com.application.writer;

import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;

import com.application.config.property.BatchConfig;
import com.application.model.EventModel;

public class FileWriter implements ItemWriter<EventModel> {

	@Autowired
	private BatchConfig ftpBatchConfig;
	private String generatedFile;
	private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	private static final DateTimeFormatter fileNamePrfix = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
	private FlatFileItemWriter<EventModel> flatFileWriter = new FlatFileItemWriter<>();

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		generatedFile = ftpBatchConfig.getFileName() + LocalDateTime.now().format(fileNamePrfix) + ".txt";
		flatFileWriter.setResource(new FileSystemResource(ftpBatchConfig.getFileOutPutDirectory() + generatedFile));
		flatFileWriter.setAppendAllowed(true);
		flatFileWriter.setLineSeparator(System.lineSeparator());
		flatFileWriter.setLineAggregator(new DelimitedLineAggregator<EventModel>() {
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
		flatFileWriter.setHeaderCallback(new FlatFileHeaderCallback() {
			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.write("Level Date and Time Source EventId TaskCatagory " + LocalDateTime.now().format(format));

			}
		});

		flatFileWriter.open(stepExecution.getExecutionContext());
	}

	@AfterStep
	public ExitStatus afterStep(StepExecution stepExecution) {
		// Create Footer
		flatFileWriter.setFooterCallback(new FlatFileFooterCallback() {
			@Override
			public void writeFooter(Writer writer) throws IOException {
				writer.write("Completed  " + LocalDateTime.now().format(format));
			}
		});
		stepExecution.getExecutionContext().putString("generatedFileName", generatedFile);
		flatFileWriter.close();
		return ExitStatus.COMPLETED;
	}

	@Override
	public void write(List<? extends EventModel> items) throws Exception {
		flatFileWriter.write(items);

	}

}
