package com.application.data.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
public class MongoDbConfiguration extends AbstractMongoConfiguration{

	private String address;
	private String dbName;
	private int port;
	private String userName;
	private String password;
	
	@Bean
	@Override
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoClient(), getDatabaseName());
	}
	
	@Override
	public MongoClient mongoClient() {
		
		MongoCredential credential = MongoCredential.createCredential(userName,getDatabaseName(),password.toCharArray());
	    ServerAddress serverAddress = new ServerAddress(address, port);
	    return new MongoClient(serverAddress,credential,MongoClientOptions.builder().build());
	}
	
	@Override
	protected String getDatabaseName() {
		return getDbName();
	}

	
	@Value("${mongo.address}")
	public void setAddress(String address) {
		this.address = address;
	}
	@Value("${mongo.dbName}")
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	@Value("${mongo.port}")
	public void setPort(int port) {
		this.port = port;
	}
	@Value("${mongo.userName}")
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Value("${mongo.password}")
	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbName() {
		return dbName;
	}
	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	
}
