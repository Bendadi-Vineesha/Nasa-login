package com.cts.login.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND, reason ="User doesn't exists with these credentials")
public class UserNotFoundException extends Exception {

	public UserNotFoundException(String message) {
		super(message);
		
	}
}
