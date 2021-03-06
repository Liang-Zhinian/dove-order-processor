package com.coogasales.core.security.authentication;

/**
 * Raised when the password has expired
 * 
 * @author Marco Meschieri - coogasales
 * @since 7.5
 */
public class PasswordExpiredException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public PasswordExpiredException() {
		super("passwordexpired");
	}

	public PasswordExpiredException(String message) {
		super(message);
	}

}
