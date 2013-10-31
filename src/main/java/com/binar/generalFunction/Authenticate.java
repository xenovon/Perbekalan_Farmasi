package com.binar.generalFunction;

import com.binar.entity.User;

public class Authenticate{
	
	public boolean login(String username, String password){
		return true;
	}
	public boolean logout(){
		return true;
	}
	public User getUserLogin(){
		return new User();
	}
	public String getRole(){
		return null;
	}
}
