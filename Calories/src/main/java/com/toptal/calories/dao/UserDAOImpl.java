package com.toptal.calories.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.toptal.calories.model.UserEntity;

@Repository
public class UserDAOImpl implements UserDAO{

	@PersistenceContext
	private EntityManager manager;

	@Override
	public void persist(UserEntity user) {
		this.manager.persist(user);
	}

	@Override
	public void remove(UserEntity user) {
		this.manager.remove(user);
	}

	@Override
	public UserEntity findById(Integer id) {
		return this.manager.find(UserEntity.class, id);
	}

	@Override
	public UserEntity findByEmail(String email) {
		TypedQuery<UserEntity> query = this.manager.createQuery("SELECT user FROM UserEntity user WHERE user.email = :email", UserEntity.class);
	
		query.setParameter("email", email);
		
		return query.getSingleResult();
	}

	@Override
	public List<UserEntity> findAll() {
		return this.manager.createQuery("SELECT user FROM UserEntity user", UserEntity.class).getResultList();
	}

}
