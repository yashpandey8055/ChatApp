package com.application.request.response.bean;

import org.springframework.http.HttpStatus;

public class GenericResponseBean {
	
	public GenericResponseBean() {
		
	}

	public GenericResponseBean(HttpStatus code, String type, Object data) {
		this.code = code;
		this.type = type;
		this.data = data;
	}
	private HttpStatus code;
	private String type;
	private Object data;
	
	public HttpStatus getCode() {
		return code;
	}
	public void setCode(HttpStatus code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
