package com.blooging.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blooging.blog.payloads.ApiResponce;

import lombok.experimental.FieldNameConstants;

@ControllerAdvice
@RestControllerAdvice //used to handle Rest API Execption Across All rest contoller 
public class GlobalExceptionHandler {
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ApiResponce> handleUserAlreadyExistException(UserAlreadyExistsException ex){
		String message = ex.getMessage();
		return new ResponseEntity<>(new ApiResponce(message, false), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ApiResponce> handelCatagoryAlreadyExistException(catagoryAlreadyExistException ex){
		String message =ex.getMessage();
		return new ResponseEntity<>(new ApiResponce(message, false), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponce> resourceNotFoundExceptionHandaler(ResourceNotFoundException ex){
		String message = ex.getMessage();
		return new ResponseEntity<>(new ApiResponce(message, false), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handelMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		Map<String, String> resp = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName = ((FieldError)error).getField();
			String message =error.getDefaultMessage();
			resp.put(fieldName, message);
		});
		return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
	}

}
