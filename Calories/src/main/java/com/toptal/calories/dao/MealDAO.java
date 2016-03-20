package com.toptal.calories.dao;

import java.util.Date;
import java.util.List;

import com.toptal.calories.model.MealEntity;
import com.toptal.calories.model.UserEntity;

public interface MealDAO {
	public void persist(MealEntity meal);
	public void remove(MealEntity meal);
	public MealEntity findById(Integer id);
	public List<MealEntity> findByFilter(UserEntity user, Date fromDate, Date toDate, Date fromTime, Date toTime);
}
