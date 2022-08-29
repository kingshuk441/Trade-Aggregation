package com.osttra.capstone.tradeaggregation.customexception;

public class FoundException extends RuntimeException {
	public FoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public FoundException(String message) {
		super(message);
	}

	public FoundException(Throwable cause) {
		super(cause);
	}
}
