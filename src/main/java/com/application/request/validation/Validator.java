package com.application.request.validation;

@FunctionalInterface
public interface Validator<T> {

	public void validateField(T field);
	
	public static boolean isNullOrEmpty(String field) {
		return field==null||field.isEmpty();
	}
	
	static boolean equals(Integer value) {
		return value == 0;
	}
	public static Validator<Integer> getNumericInstance(ValidatorType type){
		if(equals(ValidatorType.AGE.compareTo(type))) {
			Validator<Integer> v =  new AgeValidator();
			return v;
		}
		throw new IllegalArgumentException("Not a valid Field Selected. Consult ValidatorType "
				+ "enum for appropriate Numeric field. Example: AGE");
	}
	
	public static Validator<String> getStringInstance(ValidatorType type){
		if(equals(ValidatorType.USERNAME.compareTo(type))) {
			Validator<String> v =  new UserNameValidator();
			return v;
		}
		
		if(equals(ValidatorType.PASSWORD.compareTo(type))) {
			Validator<String> v =  new PasswordValidator();
			return v;
		}
		throw new IllegalArgumentException("Not a valid Field Selected. Consult ValidatorType "
				+ "enum for appropriate Numeric field. Example: USERNAME, PASSWORD");
	}
}
