package com.binar.core.inventoryManagement.deletionList.inputDeletion;

import java.util.Date;
import java.util.List;

import com.binar.core.inventoryManagement.deletionList.inputDeletion.InputDeletionView.ErrorLabel;
import com.binar.entity.Goods;
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
	int idDel;
	Window window;
	
	public InputDeletionPresenter(InputDeletionModel model, InputDeletionViewImpl view,
			GeneralFunction function){
		this.view=view;
		this.model=model;
		view.init();		
		view.hideAllError();

		view.setListener(this);
		this.generalFunction=function;
		this.view.setInputEditView(false);
		this.view.setSelectGoodsData(model.getGoodsData());
	}
	
	public InputDeletionPresenter(InputDeletionModel model, 
			InputDeletionViewImpl view, GeneralFunction function, int idDel,boolean isEdit) {
		this(model, view, function);
		updateEditView(idDel);
		view.setInputEditView(isEdit);
	}

	public void updateEditView(int idDel) {
		view.setFormData(model.getSingleDeletion(idDel));
	}	

	@Override
	public void buttonUpdate() {
		FormDeletion data=view.getFormData();
		List<String> errors=model.insertData(data);
		if(errors==null){
			view.getUI().removeWindow(window);
			view.resetForm();
			Notification.show("Penyimpanan rencana kebutuhan berhasil", Type.TRAY_NOTIFICATION);

		}else{
			String textError="Penyimpanan Tidak Berhasil, Silahkan koreksi Error berikut : </br>";
			for(String error:errors){
				textError=textError+error+"</br>";
			}
			view.showError(ErrorLabel.GENERAL, textError);
			
		}		
		
	}

	@Override
	public void buttonSave() {
		FormDeletion data=view.getFormData();
		List<String> errors=model.insertData(data);
		if(errors==null){
			view.getUI().removeWindow(window);
			view.resetForm();
			Notification.show("Penyimpanan rencana kebutuhan berhasil", Type.TRAY_NOTIFICATION);

		}else{
			String textError="Penyimpanan Tidak Berhasil, Silahkan koreksi Error berikut : </br>";
			for(String error:errors){
				textError=textError+error+"</br>";
			}
			view.showError(ErrorLabel.GENERAL, textError);
			
		}		
	}

	@Override
	public void quantityChange() {
		view.hideAllError();
		FormDeletion form=view.getFormData();
		String errorMessage=form.validateQuantity();
		
		if(!errorMessage.equals("")){
			view.showError(ErrorLabel.QUANTITY, errorMessage);
		}	
		
	}

	@Override
	public void goodsSelectChange() {
		view.hideAllError();
		FormDeletion form=view.getFormData();
		Goods goods=model.getGoods(form.getIdGoods());
		if(goods!=null){
			view.setUnit(goods.getUnit());
		}		
	}
	@Override
	public void buttonCancel() {
		generalFunction.showDialog("Batalkan", 
				"Yakin akan membatalkan memproses data penghapusan?",
				new ClickListener() {
					public void buttonClick(ClickEvent event) {
							view.getUI().removeWindow(window);
							view.resetForm();						
					}
				}, view.getUI());			
		
	}

	@Override
	public void buttonReset() {
		view.resetForm();
	}


}
