package com.binar.core.setting.settingUser;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.core.setting.settingUser.SettingUserView.FormData;
import com.binar.entity.User;
import com.binar.generalFunction.GeneralFunction;

public class SettingUserModel {
	GeneralFunction function;
	EbeanServer server;
	
	public SettingUserModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
	}

	
	public List<String> updateData(FormData data, User user){
		List<String> error;
		error=data.validate();
		if(error==null){
			error=new ArrayList<String>();
			try {
				user.setActive(true);
				user.setAddress(data.getAddress());
				user.setEmployeeNum(data.getEmployeeNum());
				user.setName(data.getName());
				if(data.getPassword1()!=null){
					user.setPasswordHash(data.getPassword1());
				}
				user.setPasswordHash(user.getPassword());
				user.setPhoneNumber(data.getPhoneNumber());
				user.setTitle(data.getTitle());
				user.setUsername(data.getUserName());
				server.update(user);
				return null;
			} catch (Exception e) {
				error.add("Gagal menyimpan data"+ e.getMessage());
				e.printStackTrace();
				return error;
			}
		}else{
			return error;
		}
	}
	
	public User getUser(int idUser){
		return server.find(User.class, idUser);
	}

}
