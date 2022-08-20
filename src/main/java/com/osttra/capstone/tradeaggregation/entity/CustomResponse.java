package com.osttra.capstone.tradeaggregation.entity;

import java.util.ArrayList;
import java.util.List;

public class CustomResponse<T> {
	private List<T> data;
	private String msg;
	private int status;

	public CustomResponse() {
		this.data = new ArrayList<>();
	}

	public CustomResponse(String msg, int status) {
		super();
		this.data = new ArrayList<>();
		this.msg = msg;
		this.status = status;
	}

	public CustomResponse(String msg, int status, T obj) {
		super();
		this.data = new ArrayList<>();
		this.msg = msg;
		this.status = status;
		this.data.add(obj);
	}

	public CustomResponse(String msg, int status, List<T> obj) {
		super();
		this.data = new ArrayList<>();
		this.msg = msg;
		this.status = status;
		this.data = obj;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
