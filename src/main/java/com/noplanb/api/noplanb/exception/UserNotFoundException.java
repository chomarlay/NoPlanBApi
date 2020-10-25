package com.noplanb.api.noplanb.exception;


public class UserNotFoundException extends RuntimeException{

	public UserNotFoundException(String message) {
		super(message);
	}
}
