package com.exam.examserver.servicesImpl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.examserver.helper.UserFoundException;
import com.exam.examserver.models.User;
import com.exam.examserver.models.UserRole;
import com.exam.examserver.repository.RoleRepository;
import com.exam.examserver.repository.UserRepository;
import com.exam.examserver.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public User createUser(User user, Set<UserRole> userRoles) throws Exception {
		
		User localUser = this.userRepository.findByUsername(user.getUsername());
		
		if(localUser != null) {
			//System.out.println("User already present");
			throw new UserFoundException();
		}else {
			//create user
			for(UserRole ur: userRoles) {
				roleRepository.save(ur.getRole());
			}
			user.getUserRoles().addAll(userRoles);
			localUser = this.userRepository.save(user);
		}
		
		return localUser;
	}

	
	//getting username
	@Override
	public User getUser(String userName) {
		
		return this.userRepository.findByUsername(userName);
	}


	@Override
	public void deleteUser(Long userId) {
		this.userRepository.deleteById(userId);
	}


	@Override
	public User updateUser(String userName) {
		User currentUser = this.userRepository.findByUsername(userName);
		User updatedUser = userRepository.save(currentUser);
		return updatedUser;
	}
	
}
