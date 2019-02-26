package com.application.boiler.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class BoilerConfiguration {

	
	@Bean
	public ObjectMapper mapper() {
		return new ObjectMapper();
	}
}
