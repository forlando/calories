package com.toptal.calories.model;

public class Meal {
	private Integer id;
	private String text;
	private String date;
	private String time;
	private Integer calories;

	public Meal() {
	}

	public Meal(String text, String date, String time, Integer calories) {
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

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getCalories() {
		return this.calories;
	}

	public void setCalories(Integer calories) {
		this.calories = calories;
	}
}
