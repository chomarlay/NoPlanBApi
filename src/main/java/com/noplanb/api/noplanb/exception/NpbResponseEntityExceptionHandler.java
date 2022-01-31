package com.noplanb.api.noplanb.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
//@ControllerAdvice
//@RestController
public class NpbResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler({AuthenticationException.class, UsernameNotFoundException.class, AccessDeniedException.class})
	public final ResponseEntity<Object> handleAuthenticationException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exceptionResponse);
//		return (new ResponseEntity(exceptionResponse, HttpStatus.FORBIDDEN));
	}
	
	@ExceptionHandler(SignupException.class)
	public final ResponseEntity<Object> handleSignupException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage() , request.getDescription(false));
//		return (new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}
	
	@ExceptionHandler({ProjectNotFoundException.class, TaskNotFoundException.class, UserNotFoundException.class })
	public final ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
//		return (new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
	}
	
	
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Validation failed..", ex.getBindingResult().toString());
//		return (new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}

}
