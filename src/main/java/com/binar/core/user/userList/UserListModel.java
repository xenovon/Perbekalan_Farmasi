package com.binar.core.user.userList;

import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.User;
import com.binar.generalFunction.GeneralFunction;

public class UserListModel {

	GeneralFunction function;
	EbeanServer server;
	public UserListModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
	}
	
	public List<User> getUser(){
		return server.find(User.class).findList();
	}
	
	public User getUser(int idUser){
		return server.find(User.class, idUser);
	}
	public String changePassword(String oldPassword, String password1, String password2){
		return null;
	}
}
