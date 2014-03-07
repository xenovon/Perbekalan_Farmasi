package com.binar.core.user.newEditUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Role;
import com.binar.entity.User;
import com.binar.generalFunction.GeneralFunction;

public interface NewEditUserView {
	
	interface NewEditUserListener{
		public void saveClick();
		public void updateClick();
		public void activationClick();
		public void resetPasswordClick();
		public void resetClick();
		public void cancelClick();
		public void valueChange();
		
	}
	
	public void init(); 
	public void construct();
	public void setListener(NewEditUserListener listener);
	public void setFormData(User data); //untuk mengeset data form
	public FormData getFormData(); //untuk mendapatkan data form
	public void setEditMode(boolean editMode); //mengubah mode edit
 	public void resetForm(); //mereset isi form
	public void showError(String content); //menampilkan error di tempat tertentu dengan konten tertentu
	public void hideError(); //menyembunyikan error tertentu
	public void setComboBoxData(ComboDataList list);

	class FormData{
		private String sika;
		private boolean editMode;
		private String userName;
		private String password1;
		private String password2;
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
		public void setEditMode(boolean editMode) {
			this.editMode = editMode;
		}
		public List<String> validate(){
			List<String> returnValue=new ArrayList<String>();
			
			if(userName==null){
				returnValue.add("Nama pengguna tidak boleh kosong");
			}else if(!isUsernameValid() && !editMode){
				returnValue.add("Nama pengguna harus unik");
			}
			if(password1==null || password2==null){
				returnValue.add("Password harus di isi");
			}else if(!password1.equals(password2)){
				returnValue.add("Password tidak sama");
			}
			
			if(role==null){
				returnValue.add("Role pengguna harus di isi");
			}else if(role.equals("IFRS") && title==null){
				returnValue.add("Jabatan harus di isi");
			}
			if(name==null){
				returnValue.add("Nama tidak boleh kosong");
			}
			return returnValue.size()==0?null:returnValue;
		}
		public boolean isUsernameValid(){
			return server.find(User.class).where().eq("username",userName).findUnique()==null?true:false;
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
		public String getSika() {
			return sika;
		}
		public void setSika(String sika) {
			this.sika = sika;
		}
	}
	
	public class ComboDataList{
		private Map<String, String> roleList;
		public void setRoleList(Map<String, String> roleList) {
			this.roleList = roleList;
		}
		public Map<String, String> getRoleList() {
			return roleList;
		}
	}
}
