package com.application.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ftp-batch.config")
public class FtpBatchConfig {

	private String dataLoadQuery;
	private String ftpUrl;
	private String fileOutPutDirectory;
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDataLoadQuery() {
		return dataLoadQuery;
	}

	public void setDataLoadQuery(String dataLoadQuery) {
		this.dataLoadQuery = dataLoadQuery;
	}

	public String getFtpUrl() {
		return ftpUrl;
	}

	public void setFtpUrl(String ftpUrl) {
		this.ftpUrl = ftpUrl;
	}

	public String getFileOutPutDirectory() {
		return fileOutPutDirectory;
	}

	public void setFileOutPutDirectory(String fileOutPutDirectory) {
		this.fileOutPutDirectory = fileOutPutDirectory;
	}

}
