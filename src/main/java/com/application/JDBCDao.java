package com.application;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class JDBCDao {

	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate template;
	
	public void someAction() {
		try {
			template = new JdbcTemplate(dataSource);
		template.execute("select * from something");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
