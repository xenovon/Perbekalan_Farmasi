package com.binar.core.inventoryManagement.deletionList.inputDeletion;

import java.util.Date;
import java.util.List;

import com.binar.core.inventoryManagement.deletionList.inputDeletion.InputDeletionView.ErrorLabel;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;

public class InputDeletionPresenter implements InputDeletionView.InputDeletionListener{

	GeneralFunction generalFunction;
	InputDeletionModel model;
	InputDeletionViewImpl view;
	FormDeletion data;
	int idDel;
	boolean editMode=false;
	Date selectedDate;
	Window window;
	
	public InputDeletionPresenter(InputDeletionModel model, InputDeletionViewImpl view,
			GeneralFunction function, String periode, Window window){
		this.view=view;
		this.model=model;
		view.init();
		this.window=window;
		view.addListener(this);
		
		this.generalFunction=function;
		this.data=new FormDeletion(function);
		this.data.setPeriode(periode);
		
		view.resetForm();
		view.setSelectGoodsData(model.getGoodsData());
		selectedDate=function.getDate().parseDateMonth(periode).toDate();
		view.setSelectedMonth(selectedDate);
	}
	
	public InputDeletionPresenter(InputDeletionModel model, 
			InputDeletionViewImpl view, GeneralFunction function, String periode, Window window, int idDel,boolean isEdit) {
		this(model, view, function, periode, window );
		updateEditView(idDel,isEdit, window);
	}

	public void updateEditView(int idDel,boolean isEdit, Window window) {
		view.setDataEdit(model.getSingleDeletion(idDel));
		setEditMode(isEdit, window);		
		this.idDel=idDel;
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
		data.setdeletionDate(view.getInputDeletionDate().getValue());
		data.setQuantity(view.getInputGoodsQuantity().getValue());
		data.setGoodsId((String)view.getInputGoodsSelect().getValue());
		data.setInformation(view.getInformation().getValue());
	
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
			String status=model.saveEdit(data, idDel); //insert data
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
		if(inputField.equals("inputGoodsSelect")){
			goodsSelectChange();
		}
	}

	private void goodsSelectChange() {
		String unit=model.getGoodsUnit((String)view.getInputGoodsSelect().getValue());
		view.setUnit(unit);
	}

	private void goodsQuantityChange() {
		data.setQuantity(view.getInputGoodsQuantity().getValue());

		String errorMessage=data.validateQuantity();
		if(errorMessage.equals("")){
			view.hideError(ErrorLabel.QUANTITY);
		}else{
			view.showError(ErrorLabel.QUANTITY, errorMessage);
		}	
	}

	@Override
	public void setDeletionDate(Date deletionDate) {
		this.data.setdeletionDate(deletionDate);
	}

	@Override
	public void setPeriode(String periode, Window window) {
		this.window=window;
		this.data.setPeriode(periode);
	}


}
