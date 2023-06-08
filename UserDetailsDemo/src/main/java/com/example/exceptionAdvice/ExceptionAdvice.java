package com.example.exceptionAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.exception.ResourceNotFoundException;

@ControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleException(ResourceNotFoundException ex) {

		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);

	}
}
