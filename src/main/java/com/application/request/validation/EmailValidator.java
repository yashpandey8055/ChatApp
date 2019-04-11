package com.application.request.validation;

import java.util.regex.Pattern;

import com.application.custom.exception.IllegalFieldException;

public class EmailValidator implements Validator<String>{

	private static final String EMAIL_REGEX = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	private static final String FAILURE_MEESAGE = "Not Valid Email Address.";
	
	private Pattern pattern = Pattern.compile(EMAIL_REGEX);
	
	@Override
	public void validateField(String email) throws IllegalFieldException{
		if(Validator.isNullOrEmpty(email)||!pattern.matcher(email).matches()){
			throw new IllegalFieldException(FAILURE_MEESAGE);
		}
	}

}
