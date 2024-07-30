package com.exam.examserver.services;

import java.util.Set;

import com.exam.examserver.models.User;
import com.exam.examserver.models.UserRole;

public interface UserService {
	
	//creating user
	public User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	//get user by username
	public User getUser(String userName);
	
	//delete user by Id
	public void deleteUser(Long userId);
	
	//update user by Id
	public User updateUser(String userName);
	
}
