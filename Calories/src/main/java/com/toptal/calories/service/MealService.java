package com.toptal.calories.service;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.toptal.calories.model.Meal;
import com.toptal.calories.model.User;

public class MealService {
	
	private static AtomicInteger lastId = new AtomicInteger();
	private static ConcurrentMap<Integer, Meal> meals = new ConcurrentHashMap<Integer, Meal>();

	public Meal save(Meal meal) {
		if (meal.getId() == null) {
			meal.setId(MealService.lastId.incrementAndGet());
		}
		
		MealService.meals.put(meal.getId(), meal);

		return meal;
	}

	public void remove(Integer id) {
		MealService.meals.remove(id);
	}

	public void remove() {
		MealService.meals.clear();
	}

	public Meal get(Integer id) {
		return MealService.meals.get(id);
	}

	public Meal[] query(User user, Date fromDate, Date toDate, Date fromTime, Date toTime) {
		return MealService.meals.values().toArray(new Meal[MealService.meals.size()]);
	}
}
