package com.application.utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpUtils {
	
	
	public static <T> T unwrap(String request,Class<T> object) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(request, object);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
