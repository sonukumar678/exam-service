package com.exam.examserver.helper;

public class UserFoundException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1665158274274157382L;

	public UserFoundException() {
		super("User with this Username is already present in DB !! try with different Username");
	}
	
	public UserFoundException(String msg) {
		super(msg);
	}
}
