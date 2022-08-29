package com.osttra.capstone.tradeaggregation.entity;

public class CustomError {
	private String fieldName;
	private String violationName;
	private String msg;

	public CustomError() {
		// TODO Auto-generated constructor stub
	}

	public CustomError(String fieldName, String violationName, String msg) {
		super();
		this.fieldName = fieldName;
		this.violationName = violationName;
		this.msg = msg;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getViolationName() {
		return violationName;
	}

	public void setViolationName(String violationName) {
		this.violationName = violationName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}