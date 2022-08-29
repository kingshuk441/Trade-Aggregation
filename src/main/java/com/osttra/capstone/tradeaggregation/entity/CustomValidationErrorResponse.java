package com.osttra.capstone.tradeaggregation.entity;

import java.util.ArrayList;
import java.util.List;

public class CustomValidationErrorResponse {
	List<CustomError> errorFields;
	private int status;
	private String msg;
	private long timestamp;

	public CustomValidationErrorResponse() {
		this.errorFields = new ArrayList<>();
	}

	public CustomValidationErrorResponse(int status, String msg, long timestamp) {
		super();
		this.errorFields = new ArrayList<>();
		this.status = status;
		this.msg = msg;
		this.timestamp = timestamp;
	}

	public List<CustomError> getErrorFields() {
		return errorFields;
	}

	public void setErrorFields(List<CustomError> errorFields) {
		this.errorFields = errorFields;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
