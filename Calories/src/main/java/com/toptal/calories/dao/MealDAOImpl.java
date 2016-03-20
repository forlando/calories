package com.toptal.calories.dao;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.toptal.calories.model.MealEntity;
import com.toptal.calories.model.UserEntity;

@Repository
public class MealDAOImpl implements MealDAO {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public void persist(MealEntity meal) {
		this.manager.persist(meal);
	}

	@Override
	public void remove(MealEntity meal) {
		this.manager.remove(meal);
	}

	@Override
	public MealEntity findById(Integer id) {
		return this.manager.find(MealEntity.class, id);
	}

	@Override
	public List<MealEntity> findByFilter(UserEntity user, Date fromDate, Date toDate, Date fromTime, Date toTime) {
		StringBuilder queryString = new StringBuilder("SELECT meal FROM MealEntity meal");
		List<String> filter = new LinkedList<String>();

		if (user != null) {
			filter.add("meal.user.id = :userId");
		}

		if (fromDate != null) {
			filter.add("meal.date >= :fromDate");
		}

		if (toDate != null) {
			filter.add("meal.date <= :toDate");
		}

		if (fromTime != null) {
			filter.add("meal.time >= :fromTime");
		}

		if (toTime != null) {
			filter.add("meal.time <= :toTime");
		}

		if (filter.size() > 0) {
			queryString.append(" WHERE ").append(String.join(" AND ", filter));
		}
	
		queryString.append(" ORDER BY meal.date, meal.time ASC");
		
		
		TypedQuery<MealEntity> query = this.manager.createQuery(queryString.toString(), MealEntity.class);
		
		if (user != null) {
			query.setParameter("userId", user.getId());
		}

		if (fromDate != null) {
			query.setParameter("fromDate", fromDate, TemporalType.DATE);
		}

		if (toDate != null) {
			query.setParameter("toDate", toDate, TemporalType.DATE);
		}

		if (fromTime != null) {
			query.setParameter("fromTime", fromTime, TemporalType.TIME);
		}

		if (toTime != null) {
			query.setParameter("toTime", toTime, TemporalType.TIME);
		}

		return query.getResultList();
	}
}
