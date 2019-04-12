package com.application.request.validation;

import com.application.custom.exception.IllegalFieldException;

public class PhoneValidator implements Validator<Long> {
	private static final String FAILURE_MESSAGE = "Invalid Phone Number. Phone Number should be 10 Digits";

	@Override
	public void validateField(Long phoneNumber) throws IllegalFieldException {
		if(Long.toString(phoneNumber).length()!=10) {
			throw new IllegalFieldException(FAILURE_MESSAGE);
		}

	}

}
