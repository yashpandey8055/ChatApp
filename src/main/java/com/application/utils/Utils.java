package com.application.utils;

import java.util.Random;

public class Utils {
	

	private static final Random RANDOM = new Random();
	
	private Utils() {
		/**
		 * No constructor for Utilities
		 */
	}
	
	public static Integer isInteger(String number) {
		try {
		return Integer.parseInt(number);
		}catch(NumberFormatException e) {
			return null;
		}
	}
	
	public static Long isLong(String number) {
		try {
		return Long.parseLong(number);
		}catch(NumberFormatException e) {
			return null;
		}
	}
	
	public static Object getRealTypeValue(String value) {
		 //Check Integer
		  Object realValue = Utils.isInteger(value);
		  //Check if long
		  realValue= realValue!=null?realValue:Utils.isLong(value);
		  //Check if just a string
		  realValue= realValue!=null?realValue:value;
		  
		  return realValue;
	}
	
	
	/**
	 * Generate a rantome striing with an extension
	 * @param ext
	 * @return
	 */
	public static String randomStringGenerate(String ext) {
		String characterArray= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnaopqrst1234567890";
		StringBuilder stringBuilder = new StringBuilder();
		for(int i =0;i<10;i++) {
			stringBuilder.append(characterArray.charAt(RANDOM.nextInt(characterArray.length())));
		}
		stringBuilder.append("."+ext);
		return stringBuilder.toString();
	}
	
	public static String stringtoJson(String field,String value) {
		return "{\""+field+"\":\""+value+"\"}";
	}

}
