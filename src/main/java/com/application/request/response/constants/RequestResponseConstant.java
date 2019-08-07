package com.application.request.response.constants;

public class RequestResponseConstant {
	
	private RequestResponseConstant() {
		/**
		 * Private Constructor to hide the public one. 
		 * The constructor is not required since this class holds only
		 * constants for Request and Response
		 */
	}

	/**
	 * Messages Section
	 */
	public static final String SUCCESS_RESPONSE = "Success";
	public static final String FAILURE_RESPONSE = "Failure";
	
	
	/**
	 * Request Response Messages
	 */
	public static final String FAILED_UPLOAD_IMAGE = "Failed to Upload Image. Try again later";
	public static final String CONNECTED = "Connected";
	public static final String DISCONNECTED = "Disconnected";
	
	public static final String CONNECT_MESSAGE_CONNECT ="Connect";
	
	public static final String CONNECT_MESSAGE_REQUESTED ="Requested. Undo?";
	
	public static final String CONNECT_MESSAGE_DISCONNECT ="Disconnect";
}
