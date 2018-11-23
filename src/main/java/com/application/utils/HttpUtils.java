package com.application.utils;

import java.io.IOException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpUtils {
	
	private static RestTemplate restTemplate = new RestTemplate();
	
	private HttpUtils() {
		/**
		 * Empty constructor of utility class 
		 */
	}
	private static final String ACCEPT_HEADER = "Accept";
	public static <T> T unwrap(String request,Class<T> object) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(request, object);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T get(String absoluteUrl,Class<T> classType) {
		try{
			HttpHeaders getRequestheaders = new HttpHeaders();
			getRequestheaders.set(ACCEPT_HEADER, MediaType.APPLICATION_JSON_VALUE);
			getRequestheaders.set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
			HttpEntity<?> getRequestEntity = new HttpEntity<>(getRequestheaders);
			ResponseEntity<T> response = 
					restTemplate.getForEntity(absoluteUrl,classType);
			return response.getBody();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
