package com.blooging.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blooging.blog.payloads.ApiResponce;

@ControllerAdvice
@RestControllerAdvice //used to handle Rest API Execption Across All rest contoller 
public class GlobalExceptionHandler {
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ApiResponce> handleUserAlreadyExistException(UserAlreadyExistsException ex){
		String message = ex.getMessage();
		return new ResponseEntity<>(new ApiResponce(message, false), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponce> resourceNotFoundExceptionHandaler(ResourceNotFoundException ex){
		String message = ex.getMessage();
		return new ResponseEntity<>(new ApiResponce(message, false), HttpStatus.NOT_FOUND);
	}

}
