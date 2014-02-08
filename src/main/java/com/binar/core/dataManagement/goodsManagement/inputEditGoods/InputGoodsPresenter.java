package com.binar.core.dataManagement.goodsManagement.inputEditGoods;

import java.util.Collection;
import java.util.List;

import com.binar.core.dataManagement.goodsManagement.inputEditGoods.InputGoodsView.ErrorLabel;
import com.binar.core.dataManagement.goodsManagement.inputEditGoods.InputGoodsView.InputGoodsListener;
import com.binar.entity.Goods;
import com.binar.entity.enumeration.EnumGoodsCategory;
import com.binar.entity.enumeration.EnumGoodsType;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class InputGoodsPresenter implements InputGoodsListener{

	private InputGoodsModel model;
	private InputGoodsViewImpl view;
	private GeneralFunction function;
	private boolean editMode;
	
	public InputGoodsPresenter(InputGoodsModel model,
			InputGoodsViewImpl view, GeneralFunction function,  boolean editMode) {
		this.model=model;
		this.view=view;
		this.function=function;
	
		view.setListener(this);
		view.init();
		view.setComboBoxData(model.getComboData());
		view.setEditMode(editMode);
		this.editMode=editMode;
	}

	@Override
	public void buttonClick(String button) {
		if(button.equals("buttonSubmit")){
			saveData();
		}else if(button.equals("buttonCancel")){
			buttonCancel();
		}else if(button.equals("buttonSaveEdit")){
			editData();
		}else if(button.equals("buttonReset")){
			view.resetForm();
			view.hideAllError();
		}
	}

	@Override
	public void realTimeValidator(String inputFields) {
		FormData data=view.getFormData();
		if(inputFields.equals("inputHET")){
			validateHET(data);
		}else if(inputFields.equals("inputId")){
			validateInputId(data);
		}else if(inputFields.equals("inputMinimalStock")){
			validateInputMinimalStock(data);
		}else if(inputFields.equals("inputInitialStock")){
			validateInputInitialStock(data);
		}

	}
	private void validateHET(FormData data){
		view.hideError(ErrorLabel.HET);
		String valid=data.validateHET();
		if(valid!=null){
			view.showError(ErrorLabel.HET, valid);
		}
	}
	private void validateInputId(FormData data){
		view.hideError(ErrorLabel.ID);
		String valid=data.validateId();
		if(valid!=null){
			view.showError(ErrorLabel.ID, valid);
		}	}
	private void validateInputMinimalStock(FormData data){
		view.hideError(ErrorLabel.MINIMUM_STOCK);
		String valid=data.validateMinimalStock();
		if(valid!=null){
			view.showError(ErrorLabel.MINIMUM_STOCK, valid);
		}		
	}
	private void validateInputInitialStock(FormData data){
		view.hideError(ErrorLabel.INITIAL_STOCK);
		String valid=data.validateInitialStock();
		if(valid!=null){
			view.showError(ErrorLabel.INITIAL_STOCK, valid);
		}
	}
	
	public boolean isCanEditInitialStock(){
		return model.isCanEditInitialStock(view.getFormData().getId());
	}
	
	private void saveData(){
		view.hideAllError();
		FormData data=view.getFormData();
		List<String> validate=data.validate();
		if(validate==null){
			String result=model.saveData(data);
			if(result==null){
				closeWindow();
				Notification.show("Penyimpanan barang berhasil");
			}else{
				view.showError(ErrorLabel.GENERAL, result);
			}
			
		}else{
			String error="<b>Tidak bisa menyimpan data, perbaiki kesalahan berikut :</b><br> <br>";
			for(String err:validate){
				error=error+validate+"<br>";
			}
			view.showError(ErrorLabel.GENERAL, error);
		}
	}
	
	private void editData(){
		view.hideAllError();
		FormData data=view.getFormData();
		List<String> validate=data.validate();
		if(validate==null){
			String result=model.saveEditData(data);
			if(result==null){
				closeWindow();
				Notification.show("Penyimpanan barang berhasil");
			}else{
				view.showError(ErrorLabel.GENERAL, result);
			}
			
		}else{
			String error="<b>Tidak bisa menyimpan data, perbaiki kesalahan berikut :</b><br> <br>";
			for(String err:validate){
				error=error+err+"<br>";
			}
			view.showError(ErrorLabel.GENERAL, error);
		}
	}
	private void buttonCancel(){
		function.showDialog("Batalkan", 
				editMode?"Anda yakin akan membatalkan perubahan data?":"Anda yakin Akan Membatalkan Memasukan Data?",
				new ClickListener() {
					public void buttonClick(ClickEvent event) {
						closeWindow();
					}
				}, view.getUI());
		
	}
	public void closeWindow(){
		Collection<Window> list=view.getUI().getWindows();
		for(Window w:list){
			view.getUI().removeWindow(w);
			view.resetForm();
		}
		
	}
	//set data untuk edit
	public void setFormData(String idGoods){
		Goods goods=model.getSingleGoods(idGoods);
		view.setFormData(goods);
	}

	@Override
	public void goodsTypeChange(EnumGoodsType type) {
		if(type==EnumGoodsType.ALAT_KESEHATAN ||
		   type==EnumGoodsType.BMHP){
			view.getInputCategory().setValue(EnumGoodsCategory.LAINNYA);
			view.setEnabled(false);

		}else{
			view.getInputCategory().setValue(EnumGoodsCategory.PATEN);
			view.setEnabled(true);
			
		}
	}

}
