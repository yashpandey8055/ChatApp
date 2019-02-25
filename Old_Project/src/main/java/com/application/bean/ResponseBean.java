package com.application.bean;

public class ResponseBean {

	private int status;
	private String message;
	
	public ResponseBean(int status,String message) {
		this.status = status;
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "status:"+this.status+" "+"message:"+this.message;
		
	}
}
