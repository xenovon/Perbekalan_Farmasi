package com.binar.core.user.userList;

import java.util.Collection;
import java.util.List;

import com.binar.core.user.newEditUser.NewEditUserModel;
import com.binar.core.user.newEditUser.NewEditUserPresenter;
import com.binar.core.user.newEditUser.NewEditUserViewImpl;
import com.binar.core.user.userList.UserListView.UserListViewListener;
import com.binar.entity.User;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class UserListPresenter implements UserListViewListener{

	UserListModel model;
	UserListViewImpl view;
	GeneralFunction function;
	 
	
	NewEditUserModel modelInput;
	NewEditUserViewImpl viewInput;
	NewEditUserPresenter presenterInput;

	NewEditUserModel modelEdit;
	NewEditUserViewImpl viewEdit;
	NewEditUserPresenter presenterEdit;
	
	public UserListPresenter(UserListModel model, UserListViewImpl view, GeneralFunction function) {
		this.model=model;
		this.view=view;
		this.function=function;
		view.init();
		view.setListener(this);
		updateTable();
	}
	@Override
	public void buttonClick(String buttonName) {
		if(buttonName.equals("buttonInput")){
			showInputWindow();
		}

	}
	private void showInputWindow(){
		if(presenterInput==null){
			modelInput =new NewEditUserModel(function);
			viewInput = new NewEditUserViewImpl(function);
			presenterInput=new NewEditUserPresenter(modelInput, viewInput, function, false);
		}
		System.out.println("Show input window");
		viewInput.resetForm();		
		view.displayForm(viewInput, "Masukan Data Pengguna Baru");
		addWIndowCloseListener();
	}

	@Override
	public void editClick(int idUser) {
		if(presenterEdit==null){
			modelEdit =new NewEditUserModel(function);
			viewEdit = new NewEditUserViewImpl(function);
			presenterEdit=new NewEditUserPresenter(modelEdit, viewEdit, function, true);
		}
		viewEdit.resetForm();	
		presenterEdit.setFormData(idUser);
		view.displayForm(viewEdit, "Ubah Data Pengguna");
		addWIndowCloseListener();
	}

	@Override
	public void deleteClick(int idUser) {
		final int finalIdUser=idUser;
		User user=model.getUser(idUser);
		function.showDialog("Hapus Data", "Yakin akan menghapus data pengguna "+user.getName(),
				new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						String result=model.deleteUser(finalIdUser);
						if(result!=null){
							Notification.show(result, Type.ERROR_MESSAGE);
						}else{
							updateTable();
							Notification.show("Data sukses dihapus");
						}
					}
				}, view.getUI());
		
	}

	@Override
	public void showClick(int idUser) {
		view.showDetailWindow(model.getUser(idUser));
	}
	public void updateTable(){
		List<User> users=model.getUser();
		view.updateTableData(users);
	}
	public void addWIndowCloseListener(){
		Collection<Window> windows=view.getUI().getWindows();
		for(Window window:windows){
			window.addCloseListener(new CloseListener() {
				public void windowClose(CloseEvent e) {
					updateTable();
				}
			});
		}

	}

}
