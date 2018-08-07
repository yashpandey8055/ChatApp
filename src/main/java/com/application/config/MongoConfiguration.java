package com.application.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
public class MongoConfiguration extends AbstractMongoConfiguration{

	
	@Value("${mongo.address}")
	private String address;
	@Value("${mongo.dbName}")
	private String dbName;
	@Value("${mongo.port}")
	private int port;
	@Value("${mongo.userName}")
	private String userName;
	@Value("${mongo.password}")
	private String password;
	
	@Override
	public MongoClient mongoClient() {
		
		MongoCredential credential = MongoCredential.createCredential(userName,getDatabaseName(),password.toCharArray());
	    ServerAddress serverAddress = new ServerAddress(address, port);
	    return new MongoClient(serverAddress,credential,MongoClientOptions.builder().build());
	}

	@Override
	protected String getDatabaseName() {
		
		return dbName;
	}

	
	
}
