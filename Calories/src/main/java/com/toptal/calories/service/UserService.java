package com.toptal.calories.service;

import java.util.List;

import com.toptal.calories.model.UserEntity;

public interface UserService {

	public UserEntity save(UserEntity loggedUser, UserEntity user);
	public void remove(UserEntity loggedUser, String email);
	public UserEntity get(String email);
	public UserEntity get(UserEntity loggedUser, String email);
	public List<UserEntity> query(UserEntity loggedUser);

}
