package com.binar.generalFunction;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.User;
import com.vaadin.server.VaadinSession;

public class LoginManager{
	
	VaadinSession session;
	EbeanServer server;
	
	public LoginManager(VaadinSession session, EbeanServer server){
		this.session=session;
		this.server=server;
	}
	
	public boolean login(String username, String password){
		if(authenticate()){
			session.setAttribute("login", username);
			return true;
		}
		return false;
	}
	public boolean isLogin(){
		if(session.getAttribute("login")==null){
			return false;
		}else{
			return true;
		}
	}
	public String checkLogin(){
		if(session.getAttribute("login")!=null){
			return (String) session.getAttribute("login");
		}else{
			return null;
		}
	}
	public boolean logout(){
		session.setAttribute("login", null);
		return true;
	}
	public User getUserLogin(){
		return new User();
	}
	public String getRole(){
		return null;
	}
	
	private boolean authenticate(){
		return true;
	}
}
