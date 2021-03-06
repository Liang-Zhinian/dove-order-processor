package com.coogasales.core.security.spring;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

/**
 * Our customization of an <code>AuthenticationDetailsSource</code> used to
 * extract a third authentication parameter for the 2FA.
 * 
 * @author Marco Meschieri - coogasales
 * @since 7.7.3
 */
public class LDAuthenticationDetailsSource extends WebAuthenticationDetailsSource {
	@Override
	public LDAuthenticationDetails buildDetails(HttpServletRequest request) {
		return new LDAuthenticationDetails(request);
	}
}
