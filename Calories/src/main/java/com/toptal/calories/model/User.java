package com.toptal.calories.model;

public class User {
	private String login;
	private String firstName;
	private String lastName;
	private Integer dailyCalories;
	private Meal[] meals;

	public User() {
	}

	public User(String login, String firstName, String lastName, Integer dailyCalories, Meal[] meals) {
		this.setLogin(login);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setDailyCalories(dailyCalories);
		this.setMeals(meals);
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
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

	public Meal[] getMeals() {
		return this.meals;
	}

	public void setMeals(Meal[] meals) {
		this.meals = meals;
	}
}
