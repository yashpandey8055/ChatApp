package com.application.utils;

import java.io.IOException;


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	public static class Wrapper{
		
		public static <T> T wrap(String request,Class<T> className) {
			   ObjectMapper mapper = new ObjectMapper();
			   		try {
						JavaType collectionType = mapper.getTypeFactory().constructType(className);
			            return mapper.readValue(request, collectionType);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;
		        }
	}
}
