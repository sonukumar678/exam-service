package com.exam.examserver.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.examserver.config.JwtUtils;
import com.exam.examserver.helper.UserNotFoundException;
import com.exam.examserver.models.JwtRequest;
import com.exam.examserver.models.JwtResponse;
import com.exam.examserver.models.User;
import com.exam.examserver.servicesImpl.UserDetailsServiceImpl;

@RestController
@CrossOrigin("*")
public class AuthenticateController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	//generate token
	@PostMapping("/generate-token")
	public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception{
		
		try {
			authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
			
		}catch (UserNotFoundException e) {
			e.printStackTrace();
			throw new Exception("User not found");
		}
		
		///user authencated
		UserDetails userDetails  =this.userDetailsServiceImpl.loadUserByUsername(jwtRequest.getUsername());
		String token = this.jwtUtils.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	private void authenticate(String username, String password) throws Exception {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
		try {
			authenticationManager.authenticate(authentication);
			
		}catch (DisabledException e) {
			throw new Exception("USER DESABLED"+ e.getMessage());
		}catch (BadCredentialsException e) {
			throw new Exception("Invalid "+e.getMessage());
		}
		
	}
	
	//return get current user
	@GetMapping("/current-user")
	public User getCurrentUser(Principal principal) {
		return (User)this.userDetailsServiceImpl.loadUserByUsername(principal.getName());
	}
}
