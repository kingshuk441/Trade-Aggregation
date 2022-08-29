package com.osttra.capstone.tradeaggregation.controller;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.osttra.capstone.tradeaggregation.customexception.FoundException;
import com.osttra.capstone.tradeaggregation.customexception.NotFoundException;
import com.osttra.capstone.tradeaggregation.entity.CustomError;
import com.osttra.capstone.tradeaggregation.entity.CustomErrorResponse;
import com.osttra.capstone.tradeaggregation.entity.CustomValidationErrorResponse;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<CustomValidationErrorResponse> handleException(ConstraintViolationException ex) {
		CustomValidationErrorResponse c = new CustomValidationErrorResponse();

		for (ConstraintViolation<?> cv : ex.getConstraintViolations()) {
			CustomError ce = new CustomError();
			ce.setFieldName(cv.getPropertyPath().toString());
			ce.setMsg(cv.getMessage());
			String violation = cv.getConstraintDescriptor().getAnnotation().toString();
			violation = violation.substring(violation.lastIndexOf(".") + 1, violation.indexOf("("));
			ce.setViolationName(violation);
			c.getErrorFields().add(ce);
		}
		c.setStatus(HttpStatus.BAD_REQUEST.value());
		c.setTimestamp(System.currentTimeMillis());
		c.setMsg("validation failed, Trade rejected!");

		return new ResponseEntity<>(c, HttpStatus.BAD_REQUEST);

	}

//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<CustomValidationErrorResponse> handleException(MethodArgumentNotValidException ex) {
//		CustomValidationErrorResponse c = new CustomValidationErrorResponse();
//		for (ObjectError e : ex.getBindingResult().getAllErrors()) {
//			CustomError ce = new CustomError();
//			ce.setFieldName(e.getCode());
//			ce.setViolationName("sd");
//			ce.setMsg(e.getDefaultMessage());
//			c.getErrorFields().add(ce);
//
//		}
//		System.out.println("END!!!!!!!!!!");
//
//		return new ResponseEntity<>(c, HttpStatus.BAD_REQUEST);
//	}

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
