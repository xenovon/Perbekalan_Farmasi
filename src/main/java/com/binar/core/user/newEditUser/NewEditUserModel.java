package com.binar.core.user.newEditUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.avaje.ebean.EbeanServer;
import com.binar.core.user.newEditUser.NewEditUserView.ComboDataList;
import com.binar.core.user.newEditUser.NewEditUserView.FormData;
import com.binar.entity.Role;
import com.binar.entity.User;
import com.binar.generalFunction.GeneralFunction;

public class NewEditUserModel {

	GeneralFunction function;
	EbeanServer server;
	
	public NewEditUserModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
	}
	public List<String> saveData(FormData data){
		List<String> error;
		error=data.validate();
		if(error==null){
			error=new ArrayList<String>();
			try {
				User user=new User();
				user.setActive(true);
				user.setAddress(data.getAddress());
				user.setEmployeeNum(data.getEmployeeNum());
				user.setName(data.getName());
				user.setPasswordHash(data.getPassword1());
				user.setPhoneNumber(data.getPhoneNumber());
				user.setRole(data.getRole());
				user.setTitle(data.getTitle());
				user.setUsername(data.getUserName());
				user.setSika(data.getSika());
				
				server.save(user);
				return null;
			} catch (Exception e) {
				error.add("Gagal menyimpan data "+ e.getMessage());
				e.printStackTrace();
				return error;
			}
		}else{
			return error;
		}
		
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
				user.setPassword(user.getMD5(data.getPassword1()));
				user.setPhoneNumber(data.getPhoneNumber());
				user.setRole(data.getRole());
				user.setTitle(data.getTitle());
				user.setUsername(data.getUserName());
				user.setSika(data.getSika());
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
	public String resetPassword(int idUser){
		User user=getUser(idUser);
		try {
			String password=getRandomPassword();
			System.out.println(password);
			System.out.println("password sekarang "+user.getPassword());
			user.setPassword(user.getMD5(password));
			System.out.println("password baru "+user.getPassword());
			
			server.update(user);
			System.out.println("password save "+user.getPassword());

			return password;
		} catch (Exception e) {
			e.printStackTrace();
			return "Reset Password gagal : "+e.getMessage();
		}
	}
	
	private String getRandomPassword()
	{
		char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 8; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);		    
		}
		String output = sb.toString();
		return output;	
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

	public ComboDataList getComboData(){
		ComboDataList returnValue=new ComboDataList();
		
		Map<String, String> mapRole=new HashMap<String, String>();
		
		List<Role> roles=server.find(Role.class).findList();
		
		for(Role role:roles){
			mapRole.put(role.getIdRole(), role.getRoleName());
		}
		
		returnValue.setRoleList(mapRole);
		return returnValue;
		
	}
}
