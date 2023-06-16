package com.example.exceptionAdvice;

import java.net.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.entity.Error;
import com.example.exception.ResourceNotFoundException;

@ControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Error> handleException(ResourceNotFoundException ex) {

		List<String> errorDescription = new ArrayList<>();
		errorDescription.add(ex.getMessage());
		
		Error error = Error.builder().errorCode("Not Found").errorDescription(errorDescription).timeStamp(LocalDateTime.now()).build();
		
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		
		headers.setContentType(MediaType.APPLICATION_XML);
		
		return new ResponseEntity<Error>(error, headers, HttpStatus.NOT_FOUND);
		
		

	}
	
	@ExceptionHandler(BindException.class)
	public ResponseEntity<Error> handleBindException(BindException ex) {
		
		
		BindingResult bindingResult = ex.getBindingResult();
		
		List<String> description = new ArrayList<>();
		
		for (FieldError error : bindingResult.getFieldErrors()) {
            description.add(error.getDefaultMessage());
        }
		

		if(description.size()==0) {
			description.add("validation failed");
		}

		Error error = Error.builder().errorCode("BAD REQUEST").errorDescription(description).timeStamp(LocalDateTime.now())
									 .build();
		
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_XML);

		return new ResponseEntity<Error>(error, headers, HttpStatus.BAD_REQUEST);
		
	}

}
