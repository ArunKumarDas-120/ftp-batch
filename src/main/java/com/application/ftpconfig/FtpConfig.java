package com.application.ftpconfig;

import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.ftp.dsl.Ftp;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.messaging.MessageChannel;

import com.application.config.property.FtpConfigProperty;

@Configuration
public class FtpConfig {

	@Autowired
	private FtpConfigProperty ftpProperty;
	
	
	@Bean
    public SessionFactory<FTPFile> ftpSessionFactory() {
        DefaultFtpSessionFactory sf = new DefaultFtpSessionFactory();
        sf.setHost(ftpProperty.getHost());
        sf.setPort(ftpProperty.getPort());
        sf.setUsername(ftpProperty.getUserName());
        sf.setPassword(ftpProperty.getPassword());
        return new CachingSessionFactory<FTPFile>(sf);
    }

    @Bean
    public IntegrationFlow ftpOutboundFlow() {
        return IntegrationFlows.from("logicalName")
                .handle(Ftp.outboundAdapter(ftpSessionFactory(), FileExistsMode.FAIL)
                        .useTemporaryFileName(false)
                        .fileNameExpression("headers['" + FileHeaders.FILENAME + "']")
                        .remoteDirectory("/")
                ).get();
    }
    
    @Bean
    public MessageChannel logicalName() {
    	return new DirectChannel();
    }


}
