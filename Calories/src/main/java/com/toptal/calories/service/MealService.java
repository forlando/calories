package com.toptal.calories.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.toptal.calories.model.Meal;

public class MealService {
	
	private static AtomicInteger lastId = new AtomicInteger();
	private static ConcurrentMap<Integer, Meal> meals = new ConcurrentHashMap<Integer, Meal>();

	public Meal createUpdate(Meal meal) {
		if (meal.getId() == null) {
			meal.setId(MealService.lastId.incrementAndGet());
		}
		
		MealService.meals.put(meal.getId(), meal);

		return meal;
	}

	public Integer delete(Integer id) {
		MealService.meals.remove(id);
		return id;
	}

	public void delete() {
		MealService.meals.clear();
	}

	public Meal get(Integer id) {
		return MealService.meals.get(id);
	}

	public Meal[] list() {
		return MealService.meals.values().toArray(new Meal[MealService.meals.size()]);
	}
}
