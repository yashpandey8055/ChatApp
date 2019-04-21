package com.application.utils;

public class Utils {
	
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

}
