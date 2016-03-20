package com.toptal.calories.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table (name="Meal")
public class MealEntity {

	@Id
    @GeneratedValue
    @Column(name="id", nullable=false)
	private Integer id;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userId", nullable=false)
	private UserEntity user;

    @Column(name="text", nullable=false)
	private String text;

    @Temporal(TemporalType.DATE)
    @Column(name="date", nullable=false)
	private Date date;

    @Temporal(TemporalType.TIME)
    @Column(name="time", nullable=false)
	private Date time;

    @Column(name="calories", nullable=false)
	private Integer calories;

    @Column(name="overDailyCalories", nullable=false)
	private Boolean overDailyCalories;

	public MealEntity() {
	}

	public MealEntity(Meal meal, UserEntity user) {
		this.setId(meal.getId());
		this.setUser(user);
		this.setText(meal.getText());
		this.setDate(meal.getDate());
		this.setTime(meal.getTime());
		this.setCalories(meal.getCalories());
		this.setOverDailyCalories(meal.getOverDailyCalories());
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return this.user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
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
