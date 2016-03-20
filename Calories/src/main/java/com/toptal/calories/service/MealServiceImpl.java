package com.toptal.calories.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toptal.calories.dao.MealDAO;
import com.toptal.calories.model.MealEntity;
import com.toptal.calories.model.UserEntity;

@Service
@Transactional
public class MealServiceImpl implements MealService {

	@Autowired
	MealDAO dao;

	@Override
	public MealEntity save(UserEntity loggedUser, MealEntity meal) {
		if (loggedUser.getRole().equals("Administrator") || loggedUser.getId() == meal.getUser().getId()) {
			if (meal.getId() != null) {
				MealEntity mealDB = this.dao.findById(meal.getId());
			
				if (mealDB != null) {
					mealDB.setUser(meal.getUser());
					mealDB.setText(meal.getText());
					mealDB.setDate(meal.getDate());
					mealDB.setTime(meal.getTime());
					mealDB.setCalories(meal.getCalories());
					mealDB.setOverDailyCalories(meal.getOverDailyCalories());
				} else {
					throw new IllegalArgumentException("Unknown meal id");
				}
			} else {
				this.dao.persist(meal);
			}
			
			this.checkDailyCalories(loggedUser, meal);

			return meal;
		} else {
			throw new SecurityException();
		}
	}

	@Override
	public void remove(UserEntity loggedUser, Integer id) {
		MealEntity meal = this.dao.findById(id);

		if (loggedUser.getRole().equals("Administrator") || (meal != null && loggedUser.getId() == meal.getUser().getId())) {
			if (meal != null) {
				this.dao.remove(meal);

				this.checkDailyCalories(loggedUser, meal);
			} else {
				 throw new IllegalArgumentException("Unknown meal id");
			}
		} else {
			throw new SecurityException();
		}
	}

	@Override
	public MealEntity get(UserEntity loggedUser, Integer id) {
		MealEntity meal = this.dao.findById(id);

		if (loggedUser.getRole().equals("Administrator") || (meal != null && loggedUser.getId() == meal.getUser().getId())) {
			if (meal != null) {
				return meal;
			} else {
				 throw new IllegalArgumentException("Unknown meal id");
			}
		} else {
			throw new SecurityException();
		}
	}

	@Override
	public List<MealEntity> query(UserEntity loggedUser, Date fromDate, Date toDate, Date fromTime, Date toTime) {
		if (loggedUser.getRole().equals("Administrator")) {
			return this.dao.findByFilter(null, fromDate, toDate, fromTime, toTime);
		} else {
			return this.dao.findByFilter(loggedUser, fromDate, toDate, fromTime, toTime);
		}
	}

	private void checkDailyCalories(UserEntity loggedUser, MealEntity meal) {
		List<MealEntity> mealsEntities = this.dao.findByFilter(loggedUser, meal.getDate(), meal.getDate(), null, null);

		Integer dailyCalories = 0;
		
		for (MealEntity mealEntity : mealsEntities) {
			dailyCalories += mealEntity.getCalories();
		}

		if (dailyCalories > loggedUser.getDailyCalories()) {
			for (MealEntity mealEntity : mealsEntities) {
				mealEntity.setOverDailyCalories(true);
			}
		} else {
			for (MealEntity mealEntity : mealsEntities) {
				mealEntity.setOverDailyCalories(false);
			}
		}
	}
}
