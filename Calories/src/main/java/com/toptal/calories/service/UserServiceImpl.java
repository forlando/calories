package com.toptal.calories.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.toptal.calories.dao.UserDAO;
import com.toptal.calories.model.UserEntity;

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class UserServiceImpl implements UserService {
	
	private Pattern emailValidator = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
	
	@Autowired
	private UserDAO dao;

	@Override
	public UserEntity save(UserEntity loggedUser, UserEntity user) {
		Preconditions.checkArgument(user != null, "User can't be null");
		Preconditions.checkArgument(user.getEmail() != null, "User email can't be null");
		Preconditions.checkArgument(user.getFirstName() != null, "User first name can't be null");
		Preconditions.checkArgument(user.getLastName() != null, "User last name can't be null");
		Preconditions.checkArgument(user.getDailyCalories() != null, "User daily calories can't be null");
		Preconditions.checkArgument(user.getRole() != null, "User role can't be null");
		Preconditions.checkArgument(this.emailValidator.matcher(user.getEmail()).matches(), "Invalid user email format");
		

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
		Preconditions.checkArgument(loggedUser != null, "Logged user can't be null");
		Preconditions.checkArgument(email != null, "Email can't be null");
		Preconditions.checkArgument(this.emailValidator.matcher(email).matches(), "Invalid email format");

		if (!loggedUser.getRole().equals("Regular") && !loggedUser.getEmail().equals(email)) {
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
		Preconditions.checkArgument(email != null, "Email can't be null");
		Preconditions.checkArgument(this.emailValidator.matcher(email).matches(), "Invalid email format");

		UserEntity user = this.dao.findByEmail(email);

		if (user != null) {
			return user;
		} else {
			throw new SecurityException();
		}
	}

	@Override
	public UserEntity get(UserEntity loggedUser, String email) {
		Preconditions.checkArgument(loggedUser != null, "Logged user can't be null");
		Preconditions.checkArgument(email != null, "Email can't be null");
		Preconditions.checkArgument(this.emailValidator.matcher(email).matches(), "Invalid email format");

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
		Preconditions.checkArgument(loggedUser != null, "Logged user can't be null");

		if (!loggedUser.getRole().equals("Regular")) {
			return this.dao.findAll();
		} else {
			throw new SecurityException();
		}
	}

}
