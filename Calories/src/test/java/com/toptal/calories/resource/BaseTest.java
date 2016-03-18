package com.toptal.calories.resource;

public class BaseTest {
	protected void assertEquals(Object expected, Object actual) {
		boolean value = (expected == actual)?(true):(expected.equals(actual));
		assert value;
	}
}
