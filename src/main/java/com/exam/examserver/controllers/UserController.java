package com.exam.examserver.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.examserver.helper.UserFoundException;
import com.exam.examserver.models.Role;
import com.exam.examserver.models.User;
import com.exam.examserver.models.UserRole;
import com.exam.examserver.services.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//creating user
	@PostMapping("/")
	public User createUser(@RequestBody User user) throws Exception {
		
		//encoding password with BcryptPasswordEncorder
		user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
		Set<UserRole> roles = new HashSet<>();
		Role role =new Role();
		role.setRoleId(45L);
		role.setRoleName("USER");
		
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);
		
		roles.add(userRole);
		return this.userService.createUser(user, roles);
		
	}
	
	//get user by username
	@GetMapping("/{username}")
	public User getUser(@PathVariable("username") String username) {
		return this.userService.getUser(username);
	}
	
	//delete user by Id
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable("userId") Long userId) {
		this.userService.deleteUser(userId);
	}
	
	//update user by Id
	@PutMapping("/{username}")
	public User updateUser(@PathVariable("username") String username) {
		return this.userService.updateUser(username);
	}
	
	@ExceptionHandler(UserFoundException.class)
	public ResponseEntity<?> exceptionHandler(UserFoundException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
		
	}
}
