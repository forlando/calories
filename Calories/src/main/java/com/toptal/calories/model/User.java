package com.toptal.calories.model;

public class User {
	
	private Integer id;
	private String email;
	private String firstName;
	private String lastName;
	private Integer dailyCalories;
	private String role;

	public User() {
	}
	
	public User(String email, String firstName, String lastName, Integer dailyCalories, String role) {
		this.setEmail(email);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setDailyCalories(dailyCalories);
		this.setRole(role);
	}

	public User(UserEntity user) {
		this.setId(user.getId());
		this.setEmail(user.getEmail());
		this.setFirstName(user.getFirstName());
		this.setLastName(user.getLastName());
		this.setDailyCalories(user.getDailyCalories());
		this.setRole(user.getRole());
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getDailyCalories() {
		return this.dailyCalories;
	}

	public void setDailyCalories(Integer dailyCalories) {
		this.dailyCalories = dailyCalories;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
