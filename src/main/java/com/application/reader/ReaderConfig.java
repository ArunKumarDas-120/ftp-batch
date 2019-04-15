package com.application.reader;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import com.application.config.property.BatchConfig;
import com.application.model.EventModel;

@Configuration
public class ReaderConfig {

	@Autowired
	private DataSource datsource;
	
	@Autowired
	private BatchConfig ftpBatchConfig;
	
	
	
	@Bean
	public ItemReader<EventModel> jdbcEventReader(){
		 JdbcCursorItemReader<EventModel> dbReader = new JdbcCursorItemReader<>();
		 dbReader.setDataSource(datsource);
		 dbReader.setDriverSupportsAbsolute(true);
		 dbReader.setSql(ftpBatchConfig.getDataLoadQuery());
		 dbReader.setRowMapper(getRowMapper());
		 return dbReader;
	}
	
	@Bean
	public RowMapper<EventModel>  getRowMapper(){
		return new RowMapper<EventModel>() {

			@Override
			public EventModel mapRow(ResultSet rs, int rowNum) throws SQLException {
				EventModel model = new EventModel();
				model.setLevel(rs.getString("LEVEL"));
				model.setDateAndTime(rs.getString("DATE_AND_TIME"));
				model.setEventId(rs.getString("EVENT_ID"));
				model.setSource(rs.getString("SOURCE"));
				model.setTaskCatagory(rs.getString("TASK_CATEGORY"));
				return model;
			}
		};
	}
}
