package com.binar.core.inventoryManagement.deletionList.inputDeletion;

import java.util.Collection;
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
		System.out.println("id deletion "+idDel);
	}	

	@Override
	public void buttonUpdate() {
		FormDeletion data=view.getFormData();
		List<String> errors=model.saveEdit(data);
		if(errors==null){
			closeWindow();
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
	private void submitManyData(){
		List<FormDeletion> data=view.getFormDataList();
		//jika data terakhir belum disave ke list form data
		//Jumlahnya antara jumlah pos dan ukuran data beda
		if(data.size()!=view.getPosCount()){
			FormDeletion formData=view.getFormData();
			List<String> errors=formData.validate();
			if(errors!=null){
				String textError="Silahkan terlebih dahulu koreksi Error berikut : </br>";
				for(String error:errors){
					textError=textError+error+"</br>";
				}
				view.showError(ErrorLabel.GENERAL, textError);
			}else{
				data.add(formData);				
			}
		}
		
//		/simpan
		List<String> status=model.insertData(data); //insert data
		if(status!=null){ //penyimpanan gagal
			String textError="Silahkan terlebih dahulu koreksi Error berikut : </br>";
			for(String error:status){
				textError=textError+error+"</br>";
			}

			view.showError(ErrorLabel.GENERAL, textError);
		}else{ //penyimpanan sukses
			Collection<Window> list=view.getUI().getWindows();
			for(Window w:list){
				view.getUI().removeWindow(w);
			}
			view.resetView();
			view.resetForm();
			Notification.show("Penyimpanan Penghapusan Data Berhasil", Type.TRAY_NOTIFICATION);
		}

		
	
	}
	@Override
	public void buttonSave() {
		if(view.getFormDataList().size()!=0){
			submitManyData();
		}else{
			FormDeletion data=view.getFormData();
			List<String> errors=model.insertData(data, true);
			if(errors==null){
				closeWindow();
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
			view.setPriceGuideValue(model.generatePriceGuide(goods.getIdGoods()));
		}else{
			view.setUnit("");
			view.setPriceGuideValue("");
		}
	}
	@Override
	public void buttonCancel() {
		generalFunction.showDialog("Batalkan", 
				"Yakin akan membatalkan memproses data penghapusan?",
				new ClickListener() {
					public void buttonClick(ClickEvent event) {
							closeWindow();
							view.resetForm();						
					}
				}, view.getUI());			
		
	}

	@Override
	public void buttonReset() {
		view.resetForm();
	}
	public void closeWindow(){
		Collection<Window> list=view.getUI().getWindows();
		for(Window w:list){
			view.getUI().removeWindow(w);
			view.resetForm();
		}
	}


}
