package com.binar.core.setting.settingInsurance;

import java.util.Collection;
import java.util.List;

import com.binar.core.setting.settingInsurance.InsuranceManagementView.InsuranceManagementListener;
import com.binar.core.setting.settingInsurance.inputEditInsurance.InputInsuranceModel;
import com.binar.core.setting.settingInsurance.inputEditInsurance.InputInsurancePresenter;
import com.binar.core.setting.settingInsurance.inputEditInsurance.InputInsuranceViewImpl;
import com.binar.entity.Goods;
import com.binar.entity.Insurance;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.LoginManager;
import com.google.gwt.dom.client.ModElement;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.WindowCloseListener;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class InsuranceManagementPresenter implements InsuranceManagementListener {

	InsuranceManagementModel model;
	InsuranceManagementViewImpl view;
	GeneralFunction function;
	
	InputInsuranceViewImpl viewInput;
	InputInsuranceModel modelInput;
	InputInsurancePresenter presenterInput;
	
	InputInsuranceViewImpl viewEdit;
	InputInsurancePresenter presenterEdit;
	
	LoginManager loginManager;

	public InsuranceManagementPresenter(InsuranceManagementModel model, 
									InsuranceManagementViewImpl view, GeneralFunction function) {
		this.model=model;
		this.view=view;
		this.function=function;
		view.init();
		this.loginManager=function.getLogin();

		view.setListener(this);
		roleProcessor();
		updateTable();
	
	}
	/*
	 *  Manajemen ROLE level Fungsionalitas
	 *   
	 */
	boolean withEditGoods = false;
	public void roleProcessor(){
		String role=loginManager.getRoleId();
		if(!role.equals(loginManager.FRM)){
			view.hideButtonNew();
			withEditGoods=false;
		}else{
			view.showButtonNew();
			withEditGoods=true;
		}
		
	}

	@Override
	public void buttonClick(String buttonName) {
		if(buttonName.equals("buttonInput")){
			showInputWindow();
		}
	}
	private void showInputWindow(){
		if(presenterInput==null){
			modelInput =new InputInsuranceModel(function);
			viewInput = new InputInsuranceViewImpl(function);
			presenterInput=new InputInsurancePresenter(modelInput, viewInput, function, false);
		}
		viewInput.resetForm();		
		view.displayForm(viewInput, "Masukan Data Barang Baru");
		addWIndowCloseListener();
	}
	@Override
	public void deleteClick(int idInsurance) {
		final int finalIdInsurance=idInsurance;
		Insurance insurance=model.getSingleInsurance(idInsurance);
		function.showDialog("Hapus Data", "Yakin akan menghapus data asuransi "+insurance.getName(),
				new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						String result=model.deleteInsurance(finalIdInsurance);
						if(result!=null){
							Notification.show(result, Type.ERROR_MESSAGE);
						}else{
							updateTable();
							Notification.show("Data sukses dihapus");
						}
					}
				}, view.getUI());
	}

	public void updateTable(){
		List<Insurance> insurance=model.getInsurance();
		view.updateTableData(insurance);
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

	@Override
	public void editClick(int idInsurance) {
		if(presenterEdit==null){
			if(modelInput==null){				
				modelInput =new InputInsuranceModel(function);
			}
			viewEdit = new InputInsuranceViewImpl(function);
			presenterEdit=new InputInsurancePresenter(modelInput, viewEdit, function, true);
		}
		viewEdit.resetForm();	
		presenterEdit.setFormData(idInsurance);
		view.displayForm(viewEdit, "Ubah Data Barang");
		addWIndowCloseListener();
		
	}

}
