package com.toptal.calories.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toptal.calories.dao.UserDAO;
import com.toptal.calories.model.UserEntity;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDAO dao;

	@Override
	public UserEntity save(UserEntity loggedUser, UserEntity user) {
		if (loggedUser == null || !loggedUser.getRole().equals("Regular") || loggedUser.getId() == user.getId()) {
			if (user.getId() != null) {
				UserEntity userDB = this.dao.findById(user.getId());
			
				if (userDB != null) {
					userDB.setEmail(user.getEmail());
					userDB.setFirstName(user.getFirstName());
					userDB.setLastName(user.getLastName());
					userDB.setDailyCalories(user.getDailyCalories());
					userDB.setRole(user.getRole());
				} else {
					throw new IllegalArgumentException("Unknown user id");
				}
			} else {
				this.dao.persist(user);
			}

			return user;
		} else {
			throw new SecurityException();
		}
	}

	@Override
	public void remove(UserEntity loggedUser, String email) {
		if (!loggedUser.getRole().equals("Regular")) {
			UserEntity user = this.dao.findByEmail(email);

			if (user != null) {
				this.dao.remove(user);
			} else {
				 throw new IllegalArgumentException("Unknown user email");
			}
		} else {
			throw new SecurityException();
		}
	}

	@Override
	public UserEntity get(String email) {
		UserEntity user = this.dao.findByEmail(email);

		if (user != null) {
			return user;
		} else {
			throw new SecurityException();
		}
	}

	@Override
	public UserEntity get(UserEntity loggedUser, String email) {
		UserEntity user = this.dao.findByEmail(email);
		
		if (!loggedUser.getRole().equals("Regular") || (user != null && loggedUser.getId() == user.getId())) {
			if (user != null) {
				return user;
			} else {
				 throw new IllegalArgumentException("Unknown user email");
			}
		} else {
			throw new SecurityException();
		}
	}

	@Override
	public List<UserEntity> query(UserEntity loggedUser) {
		if (!loggedUser.getRole().equals("Regular")) {
			return this.dao.findAll();
		} else {
			throw new SecurityException();
		}
	}

}
