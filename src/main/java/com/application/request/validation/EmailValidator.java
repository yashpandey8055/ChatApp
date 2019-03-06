package com.application.request.validation;

import java.util.regex.Pattern;

public class EmailValidator implements Validator<String>{

	private static final String EMAIL_REGEX = "^(([^<>()[\\]\\\\.,;:\\s@\\\"]+(\\.[^<>()[\\]\\\\.,;:\\s@\\\"]+)*)|(\\\".+\\\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	private static final String FAILURE_MEESAGE = "Not Valid Email Address.";
	
	private Pattern pattern = Pattern.compile(EMAIL_REGEX);
	
	@Override
	public void validateField(String email) {
		if(Validator.isNullOrEmpty(email)||!pattern.matcher(email).matches()){
			throw new IllegalArgumentException(FAILURE_MEESAGE);
		}
	}

}
