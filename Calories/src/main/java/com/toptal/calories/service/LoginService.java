package com.toptal.calories.service;

import com.toptal.calories.model.UserEntity;

public interface LoginService {
	public String login(UserEntity user);
	public UserEntity getLoggedUser(String token);
	public void logout(String token);
}
