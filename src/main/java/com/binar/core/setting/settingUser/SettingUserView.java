package com.binar.core.setting.settingUser;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Role;
import com.binar.entity.User;
import com.binar.generalFunction.GeneralFunction;

public interface SettingUserView {
	
	
	interface SettingUserListener{
		public void updateClick();
		public void resetClick();
		public void valueChange();
	
	}

	public void init(); 
	public void construct();
	public void setListener(SettingUserListener listener);
	public void setFormData(User data); //untuk mengeset data form
	public FormData getFormData(); //untuk mendapatkan data form
	public void showError(String content); //menampilkan error di tempat tertentu dengan konten tertentu
	public void hideError(); //menyembunyikan error tertentu

class FormData{
	private String userName;
	private String password1;
	private String password2;
	private String oldPassword;
	private String employeeNum;
	private String role;
	private String title;
	private String name;
	private String phoneNumber;
	private String address;
	private GeneralFunction function;
	private EbeanServer server;
	public FormData(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
	}
	public List<String> validate(){
		List<String> returnValue=new ArrayList<String>();
		
		if(userName==null){
			returnValue.add("Nama pengguna tidak boleh kosong");
		}
		try {
			if(password1.equals("") && password2.equals("")){
				System.out.println("password null");
			}else if(!password1.equals(password2)){
				returnValue.add("Password tidak sama");
			}else{
				System.out.println("password tidak null" + password1 +" "+ password2);

				if(!isOldPasswordValid()){
					returnValue.add("Password saat ini tidak valid");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(name==null){
			returnValue.add("Nama tidak boleh kosong");
		}
		return returnValue.size()==0?null:returnValue;
	}
	
	public boolean isOldPasswordValid(){
		User user=server.find(User.class).where().eq("username", userName).findUnique();
		if(oldPassword!=null){
			if(user.isPasswordMatch(oldPassword)){
				return true;
			}
		}
		return false;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword1() {
		return password1;
	}
	public void setPassword1(String password1) {
		this.password1 = password1;
	}
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public String getEmployeeNum() {
		return employeeNum;
	}
	public void setEmployeeNum(String employeeNum) {
		this.employeeNum = employeeNum;
	}
	public Role getRole() {
		return server.find(Role.class, role);
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
}

}
