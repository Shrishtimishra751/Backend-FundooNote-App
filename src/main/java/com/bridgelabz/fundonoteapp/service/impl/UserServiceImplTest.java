package com.bridgelabz.fundonoteapp.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.bridgelabz.fundonoteapp.model.Login;
import com.bridgelabz.fundonoteapp.model.UserDetails;

public class UserServiceImplTest {
	@Test
	public void testUserRegistration() {

		UserServiceImpl test = new UserServiceImpl();
		UserDetails user = new UserDetails();
		user.setActiveStatus(0);
		user.setEmail("mailtovinnetpatel@gmail.com");
		user.setMobileNo("9893332666");
		user.setPassword("vvvv");
		user.setUserId(90);
		user.setUserName("veenet");
		assertEquals(user, test.UserRegistration(user, null));
	}
	@Test
	public void testlogin()
	{
		UserServiceImpl test = new UserServiceImpl();
		Login lgn= new Login();
		lgn.getEmail();
	}
}
