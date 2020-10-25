package com.noplanb.api.noplanb.exception;


public class AccessDeniedException extends RuntimeException{

	public AccessDeniedException(String message) {
		super(message);
	}
}
