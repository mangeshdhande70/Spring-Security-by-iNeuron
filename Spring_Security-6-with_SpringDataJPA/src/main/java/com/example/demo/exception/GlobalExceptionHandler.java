package com.example.demo.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<Object> handlerDuplicationKeyException(DuplicateKeyException ex){
    	return new ResponseEntity<>(ex.getMessage(),HttpStatus.CONFLICT);
		
	}
	

}
