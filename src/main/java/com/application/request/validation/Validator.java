package com.application.request.validation;


@FunctionalInterface
public interface Validator {

	public void validate(String field);
	
	public static boolean isNullOrEmpty(String field) {
		return field==null||field.isEmpty();
	}
}
