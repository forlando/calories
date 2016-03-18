package com.toptal.calories.secure;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

public class TokenManager<T> {

	protected String newToken(HttpServletRequest request, T value) {
		String token = UUID.randomUUID().toString();
		request.getSession(true).setAttribute(token, value);
		return token;
	}

	@SuppressWarnings("unchecked")
	protected T readToken(HttpServletRequest request, String token) {
		return (T) request.getSession(true).getAttribute(token);
	}

	protected void removeToken(HttpServletRequest request, String token) {
		request.getSession(true).removeAttribute(token);
	}

}
