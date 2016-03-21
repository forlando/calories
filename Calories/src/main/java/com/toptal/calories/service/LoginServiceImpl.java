package com.toptal.calories.service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.toptal.calories.model.UserEntity;

@Service
@Scope("singleton")
public class LoginServiceImpl implements LoginService {

	public Map<Integer, String> tokens = new ConcurrentHashMap<Integer, String>();
	public Map<String, UserEntity> users = new ConcurrentHashMap<String, UserEntity>();

	@Override
	public String login(UserEntity user) {
		if (this.tokens.containsKey(user.getId())) {
			return this.tokens.get(user.getId());
		} else {
			String token = UUID.randomUUID().toString();
			this.tokens.put(user.getId(), token);
			this.users.put(token, user);
			return token;
		}
	}

	@Override
	public UserEntity getLoggedUser(String token) {
		UserEntity user = this.users.get(token);

		if (user == null && !token.equals("NEW")) {
			throw new SecurityException();
		}

		return user; 
	}

	@Override
	public void logout(String token) {
		if (this.users.containsKey(token)) {
			UserEntity user = this.users.remove(token);
			this.tokens.remove(user.getId());
		}
	}
	
}
