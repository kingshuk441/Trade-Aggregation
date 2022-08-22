package com.osttra.capstone.tradeaggregation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.osttra.capstone.tradeaggregation.entity.CustomErrorResponse;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<CustomErrorResponse> handleException(NotFoundException ex) {
		CustomErrorResponse c = new CustomErrorResponse();
		c.setMessage(ex.getMessage());
		c.setStatus(HttpStatus.NOT_FOUND.value());
		c.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<>(c, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<CustomErrorResponse> handleException(FoundException ex) {
		CustomErrorResponse c = new CustomErrorResponse();
		c.setMessage(ex.getMessage());
		c.setStatus(HttpStatus.CONFLICT.value());
		c.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<>(c, HttpStatus.CONFLICT);
	}

	@ExceptionHandler
	public ResponseEntity<CustomErrorResponse> handleException(Exception ex) {
		CustomErrorResponse c = new CustomErrorResponse();
		c.setMessage(ex.getMessage());
		c.setStatus(HttpStatus.BAD_REQUEST.value());
		c.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<>(c, HttpStatus.BAD_REQUEST);
	}
}
