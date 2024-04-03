package com.crs.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crs.entity.User;
import com.crs.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService uservice;
	
	
	@PutMapping("/login")
	public User login(@RequestBody User user) {
		return uservice.login(user);
	}
	
	@PutMapping("/logout")
	public boolean logout(@RequestBody User user) {
		return uservice.logout(user);
	}
	
	@PostMapping("/register")
	public boolean register(User user) {
		if(user.getUserType().equals("Admin")) {
			return false;
		}
		else if(user.getUserType().equals("Customer") || user.getUserType().equals("Manager") 
				|| user.getUserType().equals("Engineer")){
			return uservice.addUser(user);
		}
		else {
			return false;
		}
	}
	
	@GetMapping("/all")
	public List<User> getAllUsers(@RequestParam String userId){
		User user = uservice.getUserWithId(userId);
		if(user.getUserType()!=null && user.isLoginStatus() &&user.getUserType().equalsIgnoreCase("admin")) {
			return uservice.getAllUsers();
		}
		else {
			return null;
		}
	}
}
