package com.application.request.validation;

import com.application.custom.exception.IllegalFieldException;

@FunctionalInterface
public interface Validator<T> {

	public void validateField(T field) throws IllegalFieldException;
	
	public static Validator<String> getStringInstance(ValidatorType type){
		if(equalsInteger(ValidatorType.USERNAME.compareTo(type))) {
			return new UserNameValidator();
		}
		
		if(equalsInteger(ValidatorType.PASSWORD.compareTo(type))) {
			return  new PasswordValidator();
		}
		
		if(equalsInteger(ValidatorType.EMAIL.compareTo(type))) {
			return  new EmailValidator();
		}
		throw new IllegalArgumentException("Not a valid Field Selected. Consult ValidatorType "
				+ "enum for appropriate Numeric field. Example: USERNAME, PASSWORD");
	}
	
	public static Validator<Integer> getNumericInstance(ValidatorType type){
		if(equalsInteger(ValidatorType.AGE.compareTo(type))) {
			return new AgeValidator();
		}
		throw new IllegalArgumentException("Not a valid Field Selected. Consult ValidatorType "
				+ "enum for appropriate Numeric field. Example: AGE");
	}

	public static boolean isNullOrEmpty(String field) {
		return field==null||field.isEmpty();
	}
	
	static boolean equalsInteger(Integer value) {
		return value == 0;
	}
	
	static void stringFieldNotPresent(String value,String field)throws IllegalFieldException {
		if(value==null||value.isEmpty()) {
			throw new IllegalFieldException("Field missing in request:: "+field);
		}
	}

}
