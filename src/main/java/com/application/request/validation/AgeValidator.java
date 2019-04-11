package com.application.request.validation;

import java.util.Calendar;

import com.application.custom.exception.IllegalFieldException;

public class  AgeValidator implements Validator<Integer>{
	private static final String FAILURE_MESSAGE = "Age between 13 and 120 is allowed";
	private static final Integer MAXIMUM_AGE = 120;
	private static final Integer MINIMUM_AGE = 13;
	
	@Override
	public void validateField(Integer birthYear) throws IllegalFieldException {
		Integer age = Calendar.getInstance().get(Calendar.YEAR) - birthYear;
		if(age< MINIMUM_AGE || age>MAXIMUM_AGE) {
			throw new IllegalFieldException(FAILURE_MESSAGE);
		}
		
	}
	
}
