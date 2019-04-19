package com.application.request.validation;

import com.application.custom.exception.IllegalFieldException;
import com.application.utils.DateUtils;

@FunctionalInterface
public interface Validator<T> {

	
	public void validateField(T field) throws IllegalFieldException;
	
	public static boolean isNullOrEmpty(String field) {
		return field==null||field.isEmpty();
	}
	
	static void checkValidDate(Integer date,String month,Integer year) throws IllegalFieldException {
			if(DateUtils.convertToDate(date,month,year)==null) {
				throw new IllegalFieldException("Wrong date format/Invalid date. Date should be in dd/MMM/yyyy format.");
			}
	}
	
	static void stringFieldNotPresent(String value,String field)throws IllegalFieldException {
		if(value==null||value.isEmpty()) {
			throw new IllegalFieldException("Field missing in request:: "+field);
		}
	}

}
