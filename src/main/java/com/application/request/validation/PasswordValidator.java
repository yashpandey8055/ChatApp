package com.application.request.validation;

import java.util.regex.Pattern;


public class PasswordValidator implements Validator<String>{


	private static final String REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d]).{9,}$";
	private static final String FAILURE_MEESAGE = "Password doesn't match the required criteria.";
	
	private Pattern pattern = Pattern.compile(REGEX);
	
	@Override
	public void validateField(String password) {
		if(Validator.isNullOrEmpty(password)||!pattern.matcher(password).matches()){
			throw new IllegalArgumentException(FAILURE_MEESAGE);
		}
	}
}
