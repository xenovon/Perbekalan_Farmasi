package com.binar.core.user.userList;

import java.util.List;

import com.binar.entity.User;

public interface UserListView {
	interface UserListViewListener{
		public void buttonClick(String buttonName);
		public void editClick(int idUser);
		public void deleteClick(int idUser);
		public void showClick(int idUser);
	}
	
	public void init();
	public void construct();
	public boolean updateTableData(List<User> data);
	public void showDetailWindow(User user);
	public void setListener(UserListViewListener listener);

}
