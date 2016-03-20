package com.toptal.calories.secure;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;

public class SecurityToken<T> {

	protected String newToken(HttpServletRequest request, T value) {
		String token = UUID.randomUUID().toString();
		request.getSession(true).setAttribute(token, value);
		return token;
	}

	@SuppressWarnings("unchecked")
	protected T readToken(HttpServletRequest request, String token) {
		T value = (T) request.getSession(true).getAttribute(token);

		if (value == null && !token.equals("NEW")) {
			throw new WebApplicationException(401);
		}
		
		return value; 
	}

	protected void removeToken(HttpServletRequest request, String token) {
		request.getSession(true).removeAttribute(token);
	}

}
