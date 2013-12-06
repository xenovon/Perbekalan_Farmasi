package com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm;

import java.util.Collection;
import java.util.List;

import com.binar.core.PresenterInterface;
import com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm.InputFormView.ErrorLabel;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.client.ui.VNotification.HideEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class InputFormPresenter implements PresenterInterface, InputFormView.InputFormListener{

	GeneralFunction generalFunction;
	InputFormViewImpl view;
	InputFormModel model;
	FormData data;
	
	//id reqPlanning untuk mode edit
	int reqPlanning;
	boolean editMode=false;
	
	
	public InputFormPresenter(InputFormModel model, 
			InputFormViewImpl view, GeneralFunction function, String periode) {
		this.view=view;
		this.model=model;
		view.init();
		view.addListener(this);
		this.generalFunction=function;
		this.data=new FormData(function);
		this.data.setPeriode(periode);
		view.resetForm();
		
		view.setSelectGoodsData(model.getGoodsData());
		view.setSelectManufacturerData(model.getManufacturer());
		view.setSelectSupplierData(model.getSupplierData());
		
	}
	
	public InputFormPresenter(InputFormModel model, 
			InputFormViewImpl view, GeneralFunction function, String periode,  int reqPlanning) {
		this(model, view, function, periode);
		updateEditView(reqPlanning);
	}
	//untuk memperbaharui tampilan form, agar siap input dat lagi
	public void updateEditView(int reqPlanning){
		view.setDataEdit(model.getSingleReqPlanning(reqPlanning));
		setEditMode(true);		
		this.reqPlanning=reqPlanning;
	}
	public boolean isEditMode(){
		return editMode;
	}
	public void setEditMode(boolean editMode){
		this.editMode=true;
	}
	public void buttonClick(String source) {
		if(source.equals("forecast")){
			Notification.show("Tombol forecast ditekan");
		}else if(source.equals("reset")){
			view.resetForm();;
		}else if(source.equals("submit")){
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
							Collection<Window> list=view.getUI().getWindows();
							for(Window w:list){
								view.getUI().removeWindow(w);
								view.resetForm();
							}
						}
					}, view.getUI());
			
					
		}
	}
	//Override dari interface input form listener, 
	//berfungsi untuk menambahkan fungsi-fungsi validasi realtime pada isian form. 
	@Override
	public void realTimeValidator(String inputField) {
		view.hideAllError();
		if(inputField.equals("inputGoodsQuantity")){
			goodsQuantityChange();
		}
		if(inputField.equals("inputGoodsSelect")){
			goodsSelectChange();
		}
		if(inputField.equals("inputPrice")){
			goodsPriceChange();
		}
		if(inputField.equals("inputManufacturer")){
			manufacturerChange();
		}
		if(inputField.equals("inputSupplier")){
			supplierChange();
		}
	}
	//ambil data dari form, lalu diset ke variable data (kelas FormData)
	private void setData(){
		data.setGoodsId((String)view.getInputGoodsSelect().getValue());
		data.setInformation(view.getInputInformation().getValue());
		data.setManufacturId((String)view.getInputManufacturer().getValue());
		data.setPrice(view.getInputPrice().getValue());
		data.setQuantity(view.getInputGoodsQuantity().getValue());
		data.setSupplierId((String)view.getInputSupplier().getValue());
	}
	
	//Dijalankan ketika goods quantity berubah
	private void goodsQuantityChange(){
		data.setQuantity(view.getInputGoodsQuantity().getValue());
		
		//validasi quantity
		String errorMessage=data.validateQuantity();
		if(errorMessage.equals("")){
			view.hideError(ErrorLabel.QUANTITY);
		}else{
			view.showError(ErrorLabel.QUANTITY, errorMessage);
		}
	}
	//Dijalankan ketika harga berubah
	private void goodsPriceChange(){
		data.setPrice(view.getInputPrice().getValue());
		
		//validasi price
		String errorMessage=data.validatePrice();
		if(errorMessage.equals("")){
			view.hideError(ErrorLabel.SUPPLIER);
			String messageHET=data.validatePriceHET();
			if(messageHET.equals("")){
				view.hideError(ErrorLabel.SUPPLIER);
			}else{
				view.showError(ErrorLabel.SUPPLIER, messageHET);
			}
		}else{
			view.showError(ErrorLabel.SUPPLIER, errorMessage);
		}		
	}
	//Dijalankan ketika goods select berubah
	private void goodsSelectChange(){
		String unit=model.getGoodsUnit((String)view.getInputGoodsSelect().getValue());
		view.setUnit(unit);
		
	}
	//Dijalankan ketika dropdown manufacturer berubah
	private void manufacturerChange(){
		setData();
		String price=model.getGoodsPrice(data.getSupplierId(), 
					 data.getManufacturId(), data.getGoodsId());
		if(!price.equals("")){
			view.getInputPrice().setValue(price);
		}
	}
	//Dijalankan ketika goods supplier berubah
	private void supplierChange(){
		setData();
		String price=model.getGoodsPrice(data.getSupplierId(), 
					 data.getManufacturId(), data.getGoodsId());
		if(!price.equals("")){
			view.getInputPrice().setValue(price);
		}		
	}
	private void submitClick(){
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
				Collection<Window> list=view.getUI().getWindows();
				for(Window w:list){
					view.getUI().removeWindow(w);
				}	
				view.resetForm();
				Notification.show("Penyimpanan rencana kebutuhan berhasil", Type.TRAY_NOTIFICATION);
			}
		
		}
		
	}

	private void saveEditClick(){
		setData();
		List<String> errors=data.validate();
		if(errors!=null){
			String textError="Penyimpanan Perubahan Data Tidak Berhasil, Silahkan koreksi Error berikut : </br>";
			for(String error:errors){
				textError=textError+error+"</br>";
			}
			view.showError(ErrorLabel.GENERAL, textError);
		}else{
			String status=model.saveEdit(data, reqPlanning); //insert data
			if(status!=null){ //penyimpanan gagal
				view.showError(ErrorLabel.GENERAL, status);
			}else{ //penyimpanan sukses
				Collection<Window> list=view.getUI().getWindows();
				for(Window w:list){
					view.getUI().removeWindow(w);
				}	
				view.resetForm();
				Notification.show("Penyimpanan perubahan rencana kebutuhan berhasil", Type.TRAY_NOTIFICATION);
			}
		
			
		}
	}
	
	@Override
	public void setPeriode(String periode) {
		this.data.setPeriode(periode);
	}
	
	
	
}
