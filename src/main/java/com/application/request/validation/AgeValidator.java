package com.application.request.validation;

public class  AgeValidator implements Validator<Integer>{
	private static final String FAILURE_MEESAGE = "Age between 13 and 120 is allowed";
	private static Integer MAXIMUM_AGE = 120;
	private static Integer MINIMUM_AGE = 13;
	
	@Override
	public void validateField(Integer field) {
		
		if(field<MINIMUM_AGE||field>MAXIMUM_AGE) {
			throw new IllegalArgumentException(FAILURE_MEESAGE);
		}
	}
	
}
