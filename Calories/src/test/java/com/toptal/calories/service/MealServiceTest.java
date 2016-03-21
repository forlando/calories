package com.toptal.calories.service;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;

import com.toptal.calories.model.MealEntity;
import com.toptal.calories.model.UserEntity;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

@ContextConfiguration(locations ="/contextTest.xml")
public class MealServiceTest extends AbstractTransactionalTestNGSpringContextTests { 

	@Autowired
	private MealService service;
	
	@Autowired
	private UserService userService;

	@BeforeClass(dependsOnMethods={"springTestContextPrepareTestInstance"})
	public void beforeClass(ITestContext context) {
		UserEntity user = this.userService.save(null, new UserEntity("mealUser@gmail.com", "Meal", "User", 2000, "Administrator"));
		context.setAttribute("mealUser", user);
	}

	@AfterClass()
	public void afterClass(ITestContext context) {
	}

	@Test(groups="meals")
	public void testSave(ITestContext context) {
		UserEntity user = (UserEntity) context.getAttribute("mealUser");
		MealEntity meal1 = this.service.save(user, new MealEntity(user, "Meal 1", new Date(), new Date(), 500, false));
		MealEntity meal2 = this.service.save(user, new MealEntity(user, "Meal 2", new Date(), new Date(), 500, false));
		MealEntity meal3 = this.service.save(user, new MealEntity(user, "Meal 3", new Date(), new Date(), 500, false));
		MealEntity meal4 = this.service.save(user, new MealEntity(user, "Meal 4", new Date(), new Date(), 500, false));
		MealEntity meal5 = this.service.save(user, new MealEntity(user, "Meal 5", new Date(), new Date(), 500, false));
		assertNotNull(meal1);
		assertNotNull(meal2);
		assertNotNull(meal3);
		assertNotNull(meal4);
		assertNotNull(meal5);
		assertNotNull(meal1.getId());
		assertNotNull(meal2.getId());
		assertNotNull(meal3.getId());
		assertNotNull(meal4.getId());
		assertNotNull(meal5.getId());
		context.setAttribute("meal1", meal1);
	}

	@Test(groups="meals", dependsOnMethods="testSave")
	public void testGet(ITestContext context) {
		UserEntity user = (UserEntity) context.getAttribute("mealUser");
		MealEntity meal1 = (MealEntity) context.getAttribute("meal1");
		MealEntity meal2 = this.service.get(user, meal1.getId());
		assertNotNull(meal2);
		assertEquals(meal1.getUser().getEmail(), meal2.getUser().getEmail());
		assertEquals(meal1.getText(), meal2.getText());
		assertNotSame(meal1.getDate(), meal2.getDate());
		assertNotSame(meal1.getTime(), meal2.getTime());
		assertEquals(meal1.getCalories(), meal2.getCalories());
		assertNotSame(meal1.getOverDailyCalories(), meal2.getOverDailyCalories());
	}

	@Test(groups="meals", dependsOnMethods="testGet")
	public void testQuery(ITestContext context) {
		UserEntity user = (UserEntity) context.getAttribute("mealUser");
		List<MealEntity> meals = this.service.query(user, null, null, null, null);
		assertNotNull(meals);
		assertEquals(5, meals.size());
	}

	@Test(groups="meals", dependsOnMethods="testQuery")
	public void testRemove(ITestContext context) {
		UserEntity user = (UserEntity) context.getAttribute("mealUser");
		MealEntity meal1 = (MealEntity) context.getAttribute("meal1");
		this.service.remove(user, meal1.getId());
	}
}
