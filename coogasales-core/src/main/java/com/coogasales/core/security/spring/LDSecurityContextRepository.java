package com.coogasales.core.security.spring;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;

import com.coogasales.core.security.Session;
import com.coogasales.core.security.SessionManager;

/**
 * This repository avoid the use of sessions and simply use the current request
 * to store and retrieve the session ID.
 * 
 * @author Marco Meschieri - coogasales
 * @since 7.5
 */
public class LDSecurityContextRepository implements SecurityContextRepository {

	private static Map<String, HttpSession> servletSessionMapping = new HashMap<String, HttpSession>();

	@Override
	public boolean containsContext(HttpServletRequest request) {
		String sid = SessionManager.get().getSessionId(request);
		return sid != null;
	}

	@Override
	public SecurityContext loadContext(HttpRequestResponseHolder request) {
		String sid = SessionManager.get().getSessionId(request.getRequest());
		if (sid == null || !SessionManager.get().isOpen(sid))
			sid = null;

		if (sid == null)
			return SecurityContextHolder.createEmptyContext();

		Session session = SessionManager.get().get(sid);

		LDAuthenticationToken token = new LDAuthenticationToken(session.getUsername(), "", null);
		token.setSid(sid);

		SecurityContextImpl context = new SecurityContextImpl();
		context.setAuthentication(token);

		HttpSession servletSession = request.getRequest().getSession(false);
		if (servletSession != null)
			servletSessionMapping.put(sid, servletSession);

		return context;
	}

	@Override
	public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
		if (context.getAuthentication() == null)
			return;

		Object principal = context.getAuthentication().getPrincipal();

		if (principal != null && principal instanceof LDAuthenticationToken) {
			LDAuthenticationToken token = (LDAuthenticationToken) principal;
			SessionManager.get().saveSid(request, response, token.getSid());

			HttpSession servletSession = request.getSession(false);
			if (servletSession != null && servletSession.getAttribute(SessionManager.PARAM_SID) != null)
				servletSessionMapping.put(servletSession.getAttribute(SessionManager.PARAM_SID).toString(),
						servletSession);
		}
	}

	public static HttpSession getServletSession(String sid) {
		return servletSessionMapping.get(sid);
	}
}