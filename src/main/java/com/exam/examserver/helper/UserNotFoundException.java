package com.exam.examserver.helper;

public class UserNotFoundException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 691831660269270623L;

	public UserNotFoundException() {
		super("User with this Username not found in Database");
	}
	
	public UserNotFoundException(String msg) {
		super(msg);
	}
}
