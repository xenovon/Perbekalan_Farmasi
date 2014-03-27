package com.binar.core.dataManagement.supplierManagement;

import java.util.Collection;
import java.util.List;

import com.binar.core.dataManagement.goodsManagement.inputEditGoods.InputGoodsModel;
import com.binar.core.dataManagement.goodsManagement.inputEditGoods.InputGoodsPresenter;
import com.binar.core.dataManagement.goodsManagement.inputEditGoods.InputGoodsViewImpl;
import com.binar.core.dataManagement.supplierManagement.SupplierManagementView.SupplierManagementListener;
import com.binar.core.dataManagement.supplierManagement.inputEditSupplier.InputSupplierModel;
import com.binar.core.dataManagement.supplierManagement.inputEditSupplier.InputSupplierPresenter;
import com.binar.core.dataManagement.supplierManagement.inputEditSupplier.InputSupplierViewImpl;
import com.binar.entity.Goods;
import com.binar.entity.Supplier;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.LoginManager;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class SupplierManagementPresenter implements SupplierManagementListener{

	SupplierManagementModel  model;
	SupplierManagementViewImpl view;
	GeneralFunction function;
	
	InputSupplierModel modelInput;
	InputSupplierViewImpl viewInput;
	InputSupplierPresenter presenterInput;
	
	InputSupplierViewImpl viewEdit;
	InputSupplierPresenter presenterEdit;
	LoginManager loginManager;
	
	public SupplierManagementPresenter(SupplierManagementModel model, 
			SupplierManagementViewImpl view, GeneralFunction function) {
		this.model=model;
		this.view=view;
		this.function=function;
		this.loginManager=function.getLogin();

		this.view.setListener(this);
		this.view.init();
		roleProcessor();
		updateTable();
	}

	/*
	 *  Manajemen ROLE level Fungsionalitas
	 *   
	 */
	boolean withEditSupplier = false;
	public void roleProcessor(){
		String role=loginManager.getRoleId();
		if(!role.equals(loginManager.TPN)){
			view.hideButtonNew();
			withEditSupplier=false;
		}else{
			view.showButtonNew();
			withEditSupplier=true;
		}
		
	}
	@Override
	public void buttonClick(String buttonName) {
		if(buttonName.equals("buttonInput")){
			showInputWindow();
		}
	}

	@Override
	public void editClick(String idSupplier) {
		if(presenterEdit==null){
			modelInput =new InputSupplierModel(function);
			viewEdit = new InputSupplierViewImpl(function);
			presenterEdit=new InputSupplierPresenter(modelInput, viewEdit, function, true);
		}
		viewEdit.resetForm();	
		presenterEdit.setFormData(Integer.parseInt(idSupplier));
		view.displayForm(viewEdit, "Ubah Data Distributor");
		addWIndowCloseListener();
	}
	private void showInputWindow(){
		if(presenterInput==null){
			modelInput =new InputSupplierModel(function);
			viewInput = new InputSupplierViewImpl(function);
			presenterInput=new InputSupplierPresenter(modelInput, viewInput, function, false);
		}
		viewInput.resetForm();		
		view.displayForm(viewInput, "Masukan Data Produsen Baru");
		addWIndowCloseListener();

	}
	@Override
	public void deleteClick(String idSupplier) {
		final String finalIdSupplier=idSupplier;
		Supplier supplier=model.getSingleSupplier(Integer.parseInt(idSupplier));
		function.showDialog("Hapus Data", "Yakin akan menghapus data distributor "+supplier.getSupplierName(),
				new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						String result=model.deleteSupplier(finalIdSupplier);
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
	public void showClick(String idSupplier) {
		try {
			int idSup=Integer.parseInt(idSupplier);
			view.showDetailWindow(model.getSingleSupplier(idSup), model.getGoodsList(idSup));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			Notification.show("Kesalahan id distributor");
		}
		
	}
	public void updateTable(){
		view.updateTableData(model.getSuppliers(), withEditSupplier);
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
