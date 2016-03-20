package com.toptal.calories.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="User")
public class UserEntity {
	
	@Id
    @GeneratedValue
    @Column(name="id", nullable=false)
	private Integer id;

	@Column(name="email", nullable=false, unique=true)
	private String email;

	@Column(name="firstName", nullable=false)
	private String firstName;

	@Column(name="lastName", nullable=false)
	private String lastName;

	@Column(name="dailyCalories", nullable=false)
	private Integer dailyCalories;

	@Column(name="role", nullable=false)
	private String role;

	public UserEntity() {
	}

	public UserEntity(String email, String firstName, String lastName, Integer dailyCalories, String role) {
		this.setEmail(email);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setDailyCalories(dailyCalories);
		this.setRole(role);
	}

	public UserEntity(User user) {
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
