package com.crs.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crs.entity.User;
import com.crs.repository.UserRepository;

@Service
public class UserService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserRepository urep;
	
	//CREATE METHODS
	public boolean addUser(User user) {
		Optional<User> uop = urep.findById(user.getUserId());
		if(uop.isPresent()) {
			logger.error("The user -> {}, is already present! ",user);
			return false;
		}
		else {
			return urep.save(user)!=null;
		}
	}
	
	
	//READ METHODS
	public User getUserWithId(String userId) {
		Optional<User> uop = urep.findById(userId);
		if(uop.isEmpty()) {
			logger.error("User with Id -> {} is not present!!",userId);
			return null;
		}
		else {
			return uop.get();
		}
	}
	
	//UPDATE METHODS
	public boolean updateUserWithId(User user, String userId) {
		if(!user.getUserId().equals(userId)) {
			logger.error("The userId and the id of the user object do not match!! Please check the paramters and retry.");
			return false;
		}
		else {
			return urep.save(user)!=null;
		}
	}
	
	public User login(User user) {
		Optional<User> uop = urep.findById(user.getUserId());
		User existingUser;
		if(uop.isPresent()) {
			existingUser = uop.get();
			if(!existingUser.isLoginStatus()) {
				existingUser.setLoginStatus(true);
				urep.save(existingUser);
				return existingUser;	
			}
			else {
				logger.warn("The user {} already seems to be loggedIn! Please check status.",user);
				return null;
			}
		}
		else {
			logger.error("The user {} is not registered/not present in the database!!"
					+ " Please check the backend and retry.",user);
			return null;
		}
	}
	public boolean logout(User user) {
		Optional<User> uop = urep.findById(user.getUserId());
		User existingUser;
		if(uop.isPresent()) {
			existingUser = uop.get();
			
			if(existingUser.isLoginStatus()) {
				existingUser.setLoginStatus(false);
				urep.save(existingUser);
				return urep.findById(user.getUserId()).filter(u -> u.isLoginStatus()).isEmpty();
			}
			else {
				logger.error("The user {} doesn't seem to be loggedIn! Pleae check the status.",user);
				return false;
			}
		}
		else {
			logger.error("The user {} is not registered/not present in the database!!"
					+ " Please check the backend and retry.",user);
			return false;
		}
	}
	//DELETE METHODS
	
	public boolean deleteUserWithId(String userId) {
		Optional<User> uop = urep.findById(userId);
		if(uop.isEmpty()) {
			logger.error("The user with id {} is note present! Thus, deletion failed.",userId);
			return false;
		}
		else {
			urep.delete(uop.get());
			return urep.findById(userId).isEmpty();
		}
	}
}
