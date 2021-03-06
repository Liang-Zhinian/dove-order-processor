package com.coogasales.core.security.authentication;

/**
 * Raised when given password is not correct
 * 
 * @author Marco Meschieri - coogasales
 * @since 7.5
 */
public class WrongPasswordException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public WrongPasswordException() {
		super("wrongpassword");
	}

	public WrongPasswordException(String message) {
		super(message);
	}

}
