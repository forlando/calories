package com.toptal.calories.model;

import java.util.Date;

public class Meal {
	private Integer id;
	private String email;
	private String text;
	private Date date;
	private Date time;
	private Integer calories;
	private Boolean overDailyCalories;

	public Meal() {
	}

	public Meal(String email, String text, Date date, Date time, Integer calories, Boolean overDailyCalories) {
		this.setEmail(email);
		this.setText(text);
		this.setDate(date);
		this.setTime(time);
		this.setCalories(calories);
		this.setOverDailyCalories(overDailyCalories);
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

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getCalories() {
		return this.calories;
	}

	public void setCalories(Integer calories) {
		this.calories = calories;
	}

	public Boolean getOverDailyCalories() {
		return this.overDailyCalories;
	}

	public void setOverDailyCalories(Boolean overDailyCalories) {
		this.overDailyCalories = overDailyCalories;
	}
}
