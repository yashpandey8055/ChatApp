package com.application.request.validation;

public class FieldValidationFactory {
	
	private FieldValidationFactory() {
		/**
		 * Static methods for Factory of Validation Instances
		 */
	}

	public static final String ILLEGAL_FIELD_MESSAGE = "Not a valid Field Selected. Consult ValidatorType "
			+ "enum for appropriate Numeric field.";
	
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
		throw new IllegalArgumentException(ILLEGAL_FIELD_MESSAGE+" Example: USERNAME, PASSWORD");
	}
	
	public static Validator<Integer> getIntegerInstance(ValidatorType type){
		if(equalsInteger(ValidatorType.AGE.compareTo(type))) {
			return new AgeValidator();
		}
		throw new IllegalArgumentException(ILLEGAL_FIELD_MESSAGE+" Example: AGE");
	}
	
	public static Validator<Long> getLongInstance(ValidatorType type){
		if(equalsInteger(ValidatorType.PHONENUMBER.compareTo(type))) {
			return new PhoneValidator();
		}
		throw new IllegalArgumentException(ILLEGAL_FIELD_MESSAGE+" Example: PHONENUMBER");
	}
	
	static boolean equalsInteger(Integer value) {
		return value == 0;
	}
	

}
