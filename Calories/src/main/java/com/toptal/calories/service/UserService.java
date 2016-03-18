package com.toptal.calories.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.toptal.calories.model.User;

public class UserService {
	
	private static AtomicInteger lastId = new AtomicInteger();
	private static ConcurrentMap<String, User> users = new ConcurrentHashMap<String, User>();

	public User save(User user) {
		if (user.getId() == null) {
			user.setId(UserService.lastId.incrementAndGet());
		}

		UserService.users.put(user.getEmail(), user);
		return user;
	}

	public void remove(String login) {
		UserService.users.remove(login);
	}

	public void remove() {
		UserService.users.clear();
	}

	public User get(String email) {
		return UserService.users.get(email);
	}

	public User[] query() {
		return UserService.users.values().toArray(new User[UserService.users.size()]);
	}

}
