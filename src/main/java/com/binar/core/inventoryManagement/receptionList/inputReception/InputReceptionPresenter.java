package com.binar.core.inventoryManagement.receptionList.inputReception;

import java.util.Date;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.core.inventoryManagement.receptionList.inputReception.InputReceptionView.ErrorLabel;
import com.binar.entity.InvoiceItem;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;

public class InputReceptionPresenter implements InputReceptionView.InputReceptionListener{
	
	GeneralFunction generalFunction;
	InputReceptionModel model;
	EbeanServer server;
	InputReceptionViewImpl view;
	FormReception data;
	int idRecs;
	boolean editMode=false;
	Date selectedDate;
	Window window;
	
	public InputReceptionPresenter(InputReceptionModel model, InputReceptionViewImpl view,
			GeneralFunction function, String periode, Window window){
		this.view=view;
		this.model=model;
		this.server=function.getServer();
		view.init();
		this.window=window;
		view.addListener(this);
		
		this.generalFunction=function;
		this.data=new FormReception(function);
		this.data.setPeriode(periode);
		
		view.resetForm();
		view.setSelectInvoiceItemsData(model.getInvoiceItemsData());
		selectedDate=function.getDate().parseDateMonth(periode).toDate();
		view.setSelectedMonth(selectedDate);
	}
	
	public InputReceptionPresenter (InputReceptionModel model, InputReceptionViewImpl view, 
		GeneralFunction function, String periode, Window window, int idRecs,boolean isEdit) {
		this(model, view, function, periode, window );
		updateEditView(idRecs,isEdit, window);
	}
	
	public void updateEditView(int idRecs, boolean isEdit, Window window) {
		view.setDataEdit(model.getSingleReception(idRecs));
		setEditMode(isEdit, window);		
		this.idRecs=idRecs;
		this.window=window;
	}

	public void setEditMode(boolean isEdit, Window window) {
		this.editMode=isEdit;
		view.setInputEditView(isEdit);
		this.window=window;
	}
	
	private boolean isEditMode() {
		return editMode;	
	}
	
	@Override
	public void buttonClick(String source) {
		if(source.equals("reset")){
			view.resetForm();;
		}else if(source.equals("submit")){
			System.err.println("submit click, set edit mode "+isEditMode());
			if(isEditMode()){
				saveEditClick();
			}else{
				submitClick();				
			}
		}else if(source.equals("cancel")){
			generalFunction.showDialog("Batalkan", 
					isEditMode()?"Anda yakin akan membatalkan perubahan data?":"Anda yakin Akan Membatalkan Memasukan Data?",
					new ClickListener() {
						public void buttonClick(ClickEvent event) {
								view.getUI().removeWindow(window);
								view.resetForm();						
						}
					}, view.getUI());			
		}
	}

	private void submitClick() {
		setData();
		System.err.println(data.toString());	
		
		List<String> errors=data.validate();
		if(errors!=null){
			String textError="Penyimpanan Tidak Berhasil, Silahkan koreksi Error berikut : </br>";
			for(String error:errors){
				textError=textError+error+"</br>";
			}
			view.showError(ErrorLabel.GENERAL, textError);
		}else{
			String status=model.insertData(data); //insert data
			if(status!=null){ //penyimpanan gagal
				view.showError(ErrorLabel.GENERAL, status);
			}else{ //penyimpanan sukses
				view.getUI().removeWindow(window);
				view.resetForm();
				Notification.show("Penyimpanan rencana kebutuhan berhasil", Type.TRAY_NOTIFICATION);
			}		
		}
	}

	private void setData() {
		data.setExpiredDate(view.getInputExpiredDate().getValue());
		data.setQuantity(view.getInputGoodsQuantity().getValue());
		data.setInformation(view.getInformation().getValue());
		data.setReceptionDate(view.getInputReceptionDate().getValue());
		System.out.println(data.getQuantity());
	}

	private void saveEditClick() {
		setData();
		
		List<String> errors=data.validate();
		if(errors!=null){
			String textError="Penyimpanan Perubahan Data Tidak Berhasil, Silahkan koreksi Error berikut : </br>";
			for(String error:errors){
				textError=textError+error+"</br>";
			}
			view.showError(ErrorLabel.GENERAL, textError);
		}else{
			String status=model.saveEdit(data, idRecs); //insert data
			if(status!=null){ //penyimpanan gagal
				view.showError(ErrorLabel.GENERAL, status);
			}else{ //penyimpanan sukses
				view.getUI().removeWindow(window);
				view.resetForm();
				Notification.show("Penyimpanan perubahan pengeluaran harian berhasil", Type.TRAY_NOTIFICATION);
			}					
		}
	}

	@Override
	public void realTimeValidator(String inputField) {
		view.hideAllError();
		if(inputField.equals("inputGoodsQuantity")){
			goodsQuantityChange();
		}
		if(inputField.equals("inputInvoiceItemSelect")){
			invoiceItemSelectChange();
		}
	}

	private void invoiceItemSelectChange() {
		InvoiceItem invId= server.find(InvoiceItem.class,data.getInvoiceItemId());
		invId.getPurchaseOrderItem().getSupplierGoods().getGoods().getIdGoods();
		invId.getPurchaseOrderItem().getSupplierGoods().getGoods().getName();
	}

	private void goodsQuantityChange() {
		data.setQuantity(view.getInputGoodsQuantity().getValue());
		//validasi quantity
		String errorMessage=data.validateQuantity();
		if(errorMessage.equals("")){
			view.hideError(ErrorLabel.QUANTITY);
		}else{
			view.showError(ErrorLabel.QUANTITY, errorMessage);
		}	
	}

	@Override
	public void setReceptionDate(Date receptionDate) {
		this.data.setReceptionDate(receptionDate);	
		
	}

	@Override
	public void setPeriode(String periode, Window window) {
		this.window=window;
		this.data.setPeriode(periode);	
	}

}
