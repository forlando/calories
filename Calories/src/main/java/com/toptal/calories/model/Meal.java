package com.toptal.calories.model;

import java.util.Date;

public class Meal {
	private Integer id;
	private String text;
	private Date date;
	private Date time;
	private Integer calories;

	public Meal() {
	}

	public Meal(String text, Date date, Date time, Integer calories) {
		this.setText(text);
		this.setDate(date);
		this.setTime(time);
		this.setCalories(calories);
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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
}
