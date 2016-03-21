package com.toptal.calories.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.toptal.calories.dao.MealDAO;
import com.toptal.calories.model.MealEntity;
import com.toptal.calories.model.UserEntity;

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class MealServiceImpl implements MealService {

	@Autowired
	private MealDAO dao;

	@Override
	public MealEntity save(UserEntity loggedUser, MealEntity meal) {
		Preconditions.checkArgument(loggedUser != null, "Logged user can't be null");
		Preconditions.checkArgument(meal != null, "Meal can't be null");
		Preconditions.checkArgument(meal.getText() != null, "Meal text can't be null");
		Preconditions.checkArgument(meal.getDate() != null, "Meal date can't be null");
		Preconditions.checkArgument(meal.getTime() != null, "Meal time can't be null");
		Preconditions.checkArgument(meal.getCalories() != null, "Meal calories can't be null");

		if (loggedUser.getRole().equals("Administrator") || loggedUser.getId() == meal.getUser().getId()) {

			if (meal.getId() != null) {
				MealEntity mealDB = this.dao.findById(meal.getId());
			
				if (mealDB != null) {
					mealDB.setText(meal.getText());
					mealDB.setDate(meal.getDate());
					mealDB.setTime(meal.getTime());
					mealDB.setCalories(meal.getCalories());
					mealDB.setOverDailyCalories(meal.getOverDailyCalories());

					if (meal.getUser().getId() != mealDB.getUser().getId()) {
						UserEntity userDB = mealDB.getUser();
						
						mealDB.setUser(meal.getUser());
	
						this.checkDailyCalories(userDB, meal);
					}
				} else {
					throw new IllegalArgumentException("Unknown meal id");
				}
			} else {
				this.dao.persist(meal);
			}
			
			this.checkDailyCalories(meal.getUser(), meal);

			return meal;
		} else {
			throw new SecurityException();
		}
	}

	@Override
	public void remove(UserEntity loggedUser, Integer id) {
		Preconditions.checkArgument(loggedUser != null, "Logged user can't be null");
		Preconditions.checkArgument(id != null, "Meal id can't be null");

		MealEntity meal = this.dao.findById(id);

		if (loggedUser.getRole().equals("Administrator") || (meal != null && loggedUser.getId() == meal.getUser().getId())) {
			if (meal != null) {
				this.dao.remove(meal);

				this.checkDailyCalories(meal.getUser(), meal);
			} else {
				 throw new IllegalArgumentException("Unknown meal id");
			}
		} else {
			throw new SecurityException();
		}
	}

	@Override
	public MealEntity get(UserEntity loggedUser, Integer id) {
		Preconditions.checkArgument(loggedUser != null, "Logged user can't be null");
		Preconditions.checkArgument(id != null, "Meal id can't be null");

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
		Preconditions.checkArgument(loggedUser != null, "Logged user can't be null");

		if (fromDate != null && toDate != null) {
			Preconditions.checkArgument(fromDate.equals(toDate) || fromDate.before(toDate), "From date can't be after to date");
		}

		if (fromTime != null && toTime != null) {
			Preconditions.checkArgument(fromTime.equals(toTime) || fromTime.before(toTime), "From time can't be time to date");
		}

		if (loggedUser.getRole().equals("Administrator")) {
			return this.dao.findByFilter(null, fromDate, toDate, fromTime, toTime);
		} else {
			return this.dao.findByFilter(loggedUser, fromDate, toDate, fromTime, toTime);
		}
	}

	private void checkDailyCalories(UserEntity user, MealEntity meal) {
		List<MealEntity> mealsEntities = this.dao.findByFilter(user, meal.getDate(), meal.getDate(), null, null);

		Integer dailyCalories = 0;
		
		for (MealEntity mealEntity : mealsEntities) {
			dailyCalories += mealEntity.getCalories();
		}

		if (dailyCalories > user.getDailyCalories()) {
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
