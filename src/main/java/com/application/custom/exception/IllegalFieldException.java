package com.application.custom.exception;

public class IllegalFieldException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8628648040300843612L;
	
	private final String message;

	public IllegalFieldException(String message){
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
