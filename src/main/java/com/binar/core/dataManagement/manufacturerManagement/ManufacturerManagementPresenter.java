package com.binar.core.dataManagement.manufacturerManagement;

import java.util.Collection;
import java.util.List;

import com.binar.core.dataManagement.goodsManagement.inputEditGoods.InputGoodsModel;
import com.binar.core.dataManagement.goodsManagement.inputEditGoods.InputGoodsPresenter;
import com.binar.core.dataManagement.goodsManagement.inputEditGoods.InputGoodsViewImpl;
import com.binar.core.dataManagement.manufacturerManagement.ManufacturerManagementView.ManufacturerManagementListener;
import com.binar.core.dataManagement.manufacturerManagement.inputManufacturer.InputManufacturerModel;
import com.binar.core.dataManagement.manufacturerManagement.inputManufacturer.InputManufacturerPresenter;
import com.binar.core.dataManagement.manufacturerManagement.inputManufacturer.InputManufacturerViewImpl;
import com.binar.core.dataManagement.supplierManagement.SupplierManagementView.SupplierManagementListener;
import com.binar.core.dataManagement.supplierManagement.inputEditSupplier.InputSupplierModel;
import com.binar.core.dataManagement.supplierManagement.inputEditSupplier.InputSupplierPresenter;
import com.binar.core.dataManagement.supplierManagement.inputEditSupplier.InputSupplierViewImpl;
import com.binar.entity.Goods;
import com.binar.entity.Manufacturer;
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

public class ManufacturerManagementPresenter implements ManufacturerManagementListener{

	ManufacturerManagementModel  model;
	ManufacturerManagementViewImpl view;
	GeneralFunction function;
	
	InputManufacturerModel modelInput;
	InputManufacturerViewImpl viewInput;
	InputManufacturerPresenter presenterInput;
	
	InputManufacturerViewImpl viewEdit;
	InputManufacturerPresenter presenterEdit;
	LoginManager loginManager;

	
	public ManufacturerManagementPresenter(ManufacturerManagementModel model, 
			ManufacturerManagementViewImpl view, GeneralFunction function) {
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
	boolean withEditInvoice = false;
	public void roleProcessor(){
		String role=loginManager.getRoleId();
		if(!role.equals(loginManager.FRM)){
			view.hideButtonNew();
			withEditInvoice=false;
		}else{
			view.showButtonNew();
			withEditInvoice=true;
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
			modelInput =new InputManufacturerModel(function);
			viewEdit = new InputManufacturerViewImpl(function);
			presenterEdit=new InputManufacturerPresenter(modelInput, viewEdit, function, true);
		}
		viewEdit.resetForm();	
		presenterEdit.setFormData(Integer.parseInt(idSupplier));
		view.displayForm(viewEdit, "Ubah Data Distributor");
		addWIndowCloseListener();
	}
	private void showInputWindow(){
		if(presenterInput==null){
			modelInput =new InputManufacturerModel(function);
			viewInput = new InputManufacturerViewImpl(function);
			presenterInput=new InputManufacturerPresenter(modelInput, viewInput, function, false);
		}
		viewInput.resetForm();		
		view.displayForm(viewInput, "Masukan Data Distributor Baru");
		addWIndowCloseListener();

	}
	@Override
	public void deleteClick(String idManufacturer) {
		final String finalIdManufacturer=idManufacturer;
		Manufacturer manufacture=model.getSingleManufacturer(Integer.parseInt(idManufacturer));
		function.showDialog("Hapus Data", "Yakin akan menghapus data produsen "+manufacture.getManufacturerName(),
				new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						String result=model.deleteManufacturer(finalIdManufacturer);
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
	public void showClick(String idManufacturer) {
		try {
			int idMan=Integer.parseInt(idManufacturer);
			view.showDetailWindow(model.getSingleManufacturer(idMan), model.getGoodsList(idMan));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			Notification.show("Kesalahan id produsen");
		}
		
	}
	public void updateTable(){
		view.updateTableData(model.getManufacturer(), withEditInvoice);
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
