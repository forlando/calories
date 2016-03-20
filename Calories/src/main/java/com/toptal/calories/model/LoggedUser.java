package com.toptal.calories.model;

public class LoggedUser extends User {
	private String token;

	public LoggedUser() {
	}

	public LoggedUser(UserEntity user, String token) {
		super(user);
		this.setToken(token);
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
