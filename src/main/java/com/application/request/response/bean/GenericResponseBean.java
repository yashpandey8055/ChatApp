package com.application.request.response.bean;

public class GenericResponseBean {

	public GenericResponseBean(int code, String type, Object data) {
		this.code = code;
		this.type = type;
		this.data = data;
	}
	private int code;
	private String type;
	private Object data;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
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
