package com.toptal.calories.dao;

import java.util.List;

import com.toptal.calories.model.UserEntity;

public interface UserDAO {
	public void persist(UserEntity user);
	public void remove(UserEntity user);
	public UserEntity findById(Integer id);
	public UserEntity findByEmail(String email);
	public List<UserEntity> findAll();
}
