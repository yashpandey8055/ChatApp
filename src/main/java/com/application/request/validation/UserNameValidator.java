package com.application.request.validation;

import java.util.regex.Pattern;

public class UserNameValidator implements Validator<String>{

	private static final String USERNAME_REGEX = "^[a-zA-Z\\d]{0,15}$";
	private static final String FAILURE_MEESAGE = "Username should be max 15 Characters and "
			+ " should contain letter lowercase/uppercase/numeric characters only.";
	
	private Pattern pattern = Pattern.compile(USERNAME_REGEX);
	
	@Override
	public void validateField(String userName) {
		if(Validator.isNullOrEmpty(userName)||!pattern.matcher(userName).matches()){
			throw new IllegalArgumentException(FAILURE_MEESAGE);
		}
	}

}

