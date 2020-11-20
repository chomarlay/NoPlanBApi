package com.noplanb.api.noplanb.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@RestControllerAdvice
@ControllerAdvice
@RestController
public class NpbResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(AuthenticationException.class)
	public final ResponseEntity<Object> handleAuthenticationException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return (new ResponseEntity(exceptionResponse, HttpStatus.FORBIDDEN));
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public final ResponseEntity<Object> handleUsernameNotFoundException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage() + " ---" , request.getDescription(false));
		return (new ResponseEntity(exceptionResponse, HttpStatus.FORBIDDEN));
	}
	
	@ExceptionHandler(ProjectNotFoundException.class)
	public final ResponseEntity<Object> handleProjectNotFoundException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return (new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND));
	}
	
	@ExceptionHandler(TaskNotFoundException.class)
	public final ResponseEntity<Object> handleTaskNotFoundException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return (new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND));
	}	
	
	@ExceptionHandler(AccessDeniedException.class)
	public final ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return (new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND));
	}	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return (new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND));
	}
	
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Validation failed", ex.getBindingResult().toString());
		return (new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST));
	}

}
