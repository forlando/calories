package com.toptal.calories.service;

import java.util.Date;
import java.util.List;

import com.toptal.calories.model.MealEntity;
import com.toptal.calories.model.UserEntity;

public interface MealService {

	public MealEntity save(UserEntity loggedUser, MealEntity meal);
	public void remove(UserEntity loggedUser, Integer id);
	public MealEntity get(UserEntity loggedUser, Integer id);
	public List<MealEntity> query(UserEntity loggedUser, Date fromDate, Date toDate, Date fromTime, Date toTime);

}
