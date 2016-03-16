package com.toptal.calories.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.toptal.calories.model.User;

public class UserService {
	
	private static ConcurrentMap<String, User> users = new ConcurrentHashMap<String, User>();

	public User createUpdate(User user) {
		UserService.users.put(user.getLogin(), user);
		return user;
	}

	public String delete(String login) {
		UserService.users.remove(login);
		return login;
	}

	public void delete() {
		UserService.users.clear();
	}

	public User get(String login) {
		return UserService.users.get(login);
	}

	public User[] list() {
		return UserService.users.values().toArray(new User[UserService.users.size()]);
	}

}
