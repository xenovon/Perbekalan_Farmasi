package com.binar.generalFunction;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Role;
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
//		if(authenticate(username, password)){
//			session.setAttribute("login", username);
//			return true;
//		}
//		return false;
		session.setAttribute("login", "binar");
		return true;
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
		if(isLogin()){
			Object username=session.getAttribute("login");
			return server.find(User.class).where()
						 .eq("username", username.toString()).findUnique();
		}else{
			return null;
		}
	}
	public Role getRole(){
		return getUserLogin().getRole();
	}
	
	private boolean authenticate(String username, String password){
		User user=server.find(User.class).where().eq("username", username).findUnique();
		if(user!=null){
			return user.isPasswordMatch(password)?true:false;			
		}else{
			return false;
		}
	}
}
