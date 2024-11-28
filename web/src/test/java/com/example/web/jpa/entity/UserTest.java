package com.example.web.jpa.entity;

import org.junit.jupiter.api.Test;

public class UserTest {
	@Test
	public void testUser() {
		User user = new User("user1", "user123", "user1@example.com");
		System.out.println(user);
	}
}
