package com.binar.core.inventoryManagement.receptionList.inputReception;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avaje.ebean.EbeanServer;
import com.binar.core.inventoryManagement.receptionList.inputReception.InputReceptionView.ErrorLabel;
import com.binar.entity.GoodsReception;
import com.binar.entity.Invoice;
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
	FormReception formReception;
	Window window;
	public InputReceptionPresenter(InputReceptionModel model, InputReceptionViewImpl view,
			GeneralFunction function){
		this.view=view;
		this.model=model;
		this.generalFunction=function;
		this.formReception=new FormReception(this.generalFunction);
		this.server=function.getServer();
		view.addListener(this);
		view.init();
		
		this.generalFunction=function;
	}
	
	public InputReceptionPresenter (InputReceptionModel model, InputReceptionViewImpl view, 
		GeneralFunction function, int idRecs, boolean isEdit) {
		this(model, view, function);
		setEditMode(isEdit);
		updateEditView(idRecs);
		System.out.println("Is edit" + isEdit);
	}
	public void setWindow(Window window){
		this.window=window;
	}
	public void updateEditView(int idRecs) {
		this.idRecs=idRecs;
		GoodsReception reception=model.getSingleReception(idRecs);
		view.setDataEdit(reception, model.getSingleReceptionList(reception));
	}

	public void setEditMode(boolean isEdit) {
		this.editMode=isEdit;
		view.setInputEditView(isEdit);
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
								closeWindow();
								view.resetForm();						
						}
					}, view.getUI());			
		}
	}

	private void submitClick() {
		FormReception data=view.getFormData();
		List<String> errors=model.insertData(data); //insert data
		if(errors!=null){
			String textError="Penyimpanan Tidak Berhasil, Silahkan koreksi Error berikut : </br>";
			for(String error:errors){
				textError=textError+error+"</br>";
			}
			view.showError(ErrorLabel.GENERAL, textError);
		}else{
			closeWindow();
			view.resetForm();
			Notification.show("Penyimpanan penerimaan barang berhasil", Type.TRAY_NOTIFICATION);
		}
		
	}


	private void saveEditClick() {
		FormReception data=view.getFormData();
		List<String> errors=model.saveEdit(data, idRecs); //insert data
		if(errors!=null){
			String textError="Penyimpanan Tidak Berhasil, Silahkan koreksi Error berikut : </br>";
			for(String error:errors){
				textError=textError+error+"</br>";
			}
			view.showError(ErrorLabel.GENERAL, textError);
		}else{
			closeWindow();
			view.resetForm();
			Notification.show("Penyimpanan perubahan penerimaan barang berhasil", Type.TRAY_NOTIFICATION);
		}					
	}

	@Override
	public void realTimeValidator(String inputField) {
		view.hideAllError();
		if(inputField.equals("inputGoodsQuantity")){
			goodsQuantityChange();
		}
		if(inputField.equals("inputInvoiceEndDate")){
			dateRangeChange();
		}
		if(inputField.equals("inputGoodsSelect")){
			invoiceItemChange();
			System.out.println("input good select change");
		}	
		if(inputField.equals("inputInvoiceStartDate")){
			dateRangeChange();
		}
		if(inputField.equals("inputInvoiceSelect")){
			invoiceChange();
		}
	}
	private void dateRangeChange(){
		Date startDate=view.getDateRangeStart();
		Date endDate=view.getDateDateRangeEnd();
		view.setSelectInvoiceItemsData(model.getInvoiceItemsData(startDate, endDate));
	}
	
	private void goodsQuantityChange() {
		String quantity=view.getInputGoodsQuantity();
		formReception.setQuantity(quantity);
		String errorMessage=formReception.validateQuantity();
		if(errorMessage.equals("")){
			view.hideAllError();
		}else{
			view.showError(ErrorLabel.QUANTITY, errorMessage);
		}	
		System.out.println("Quantity change");
	}
	
	private void invoiceChange(){
		int invoiceSelect=view.getInvoiceSelect();
		view.setSelectGoodsData(model.getGoodsData(invoiceSelect));
	}
	private void invoiceItemChange(){
		int invoiceItemId=view.getGoodsSelect();
		InvoiceItem item=model.getSingleInvoiceItem(invoiceItemId);
		view.setUnit(item.getPurchaseOrderItem().getSupplierGoods().getGoods().getUnit());
		System.out.println(item.getPurchaseOrderItem().getSupplierGoods().getGoods().getName()+ " goods unit");
	}
	
	public void closeWindow(){
		if(isEditMode()){
			view.resetForm();
			if(window!=null){				
				view.getUI().removeWindow(window);
			}
		}else{
			Collection<Window> list=view.getUI().getWindows();
			for(Window w:list){
				view.getUI().removeWindow(w);
				view.resetForm();
			}			
		}
	}
}
