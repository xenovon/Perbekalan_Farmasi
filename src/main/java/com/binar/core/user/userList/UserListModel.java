package com.binar.core.user.userList;

import java.util.List;
import java.util.Random;

import javax.persistence.PersistenceException;

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

	public String deleteUser(int idUser){
		try {
			User user=server.find(User.class, idUser);
			server.delete(user); 
		} catch (PersistenceException e) {
			e.printStackTrace();
			return "Data Gagal Dihapus : Data pengguna sudah terhubung dengan "
					+ "data lainnya. Hapus data yang terhubung terlebih dahulu";
		}catch (Exception e){
			e.printStackTrace();
			return "Kesalahan menghapus data : "+e.getMessage();
		}
		return null;
	}
	
	public String activateUser(int idUser){
		User user=getUser(idUser);
		try {
			boolean active=user.isActive();
			user.setActive(!active);
			server.update(user);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return "Kesalahan melakukan aktivasi : "+e.getMessage();
		}
	}
}
