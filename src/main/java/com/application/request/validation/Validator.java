package com.application.request.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.application.custom.exception.IllegalFieldException;

@FunctionalInterface
public interface Validator<T> {

	
	public void validateField(T field) throws IllegalFieldException;
	
	public static boolean isNullOrEmpty(String field) {
		return field==null||field.isEmpty();
	}
	
	static void checkValidDate(Integer date,String month,Integer year) throws IllegalFieldException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
		try {
			sdf.parse(date+"/"+month+"/"+year);
		} catch (ParseException e) {
			throw new IllegalFieldException("Wrong date format/Invalid date. Date should be in dd/MMM/yyyy format.");
		}
	}
	
	static void stringFieldNotPresent(String value,String field)throws IllegalFieldException {
		if(value==null||value.isEmpty()) {
			throw new IllegalFieldException("Field missing in request:: "+field);
		}
	}

}
