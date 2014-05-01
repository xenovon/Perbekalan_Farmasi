package com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm;

import java.util.Collection;
import java.util.List;

import com.binar.core.PresenterInterface;
import com.binar.core.requirementPlanning.forecast.forecastStep.ForecastStepModel;
import com.binar.core.requirementPlanning.forecast.forecastStep.ForecastStepPresenter;
import com.binar.core.requirementPlanning.forecast.forecastStep.ForecastStepViewImpl;
import com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm.InputFormView.ErrorLabel;
import com.binar.entity.Goods;
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
	
	ForecastStepModel forecastModel;
	ForecastStepPresenter forecastPresenter;
	ForecastStepViewImpl forecastView;
	
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
		view.setPeriode(periode);
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
	private void forecastClick(){
		//jika barang tidak dipilih
		if((String)view.getInputGoodsSelect().getValue()==null){
			Notification.show("Pilih barang terlebih dahulu", Type.TRAY_NOTIFICATION);
		}else{
			if(forecastPresenter==null){
				forecastModel=new ForecastStepModel(generalFunction);
				forecastView=new ForecastStepViewImpl(generalFunction);
				forecastPresenter=new ForecastStepPresenter(forecastModel, forecastView, generalFunction, (String)view.getInputGoodsSelect().getValue());
			}
			Window window=view.displayForm(forecastView, "Peramalan");
			forecastPresenter.setWindow(window);			
		}
		
	}
	public void buttonClick(String source) {
		if(source.equals("forecast")){
			forecastClick();
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
								view.hideAllError();
							}
						}
					}, view.getUI());
			view.resetView();

			
					
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
		data.setPpn(view.getIsPPN().getValue());
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
			}else{
				view.showError(ErrorLabel.SUPPLIER, messageHET);
			}
		}else{
			view.showError(ErrorLabel.SUPPLIER, errorMessage);
		}		
	}
	//Dijalankan ketika goods select berubah
	private void goodsSelectChange(){
		String data=(String)view.getInputGoodsSelect().getValue();
		if(data!=null){
			String unit=model.getGoodsUnit(data);
			Goods goods=model.getGoods((String) view.getInputGoodsSelect().getValue());
			String het="HET "+generalFunction.getTextManipulator().doubleToRupiah(goods.getHet());
			view.showError(ErrorLabel.SUPPLIER, het);
			view.setUnit(unit);
			
		}
		
	}
	//Dijalankan ketika dropdown manufacturer berubah
	private void manufacturerChange(){
		setData();
		String supplierId=data.getSupplierId();
		String manId=data.getManufacturId();
		String gId=data.getGoodsId();
		if(supplierId!=null && manId!=null && gId!=null){
			String price=model.getGoodsPrice(supplierId, 
						 data.getManufacturId(), data.getGoodsId());
			if(!price.equals("")){
				view.getInputPrice().setValue(price);
			}
			
		}
	}
	//Dijalankan ketika goods supplier berubah
	private void supplierChange(){
		setData();
		String supplierId=data.getSupplierId();
		String manId=data.getManufacturId();
		String gId=data.getGoodsId();
		if(supplierId!=null && manId!=null && gId!=null){
			String price=model.getGoodsPrice(supplierId, 
						 data.getManufacturId(), data.getGoodsId());
			if(!price.equals("")){
				view.getInputPrice().setValue(price);
			}
			
		}	}
	private void submitClick(){
		
		if(view.getFormDataList().size()!=0){
			submitManyData();
		}else{
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
				String status=model.insertData(data, true); //insert data
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
		
		
	}
	private void submitManyData(){
		
		List<FormData> data=view.getFormDataList();
		//jika data terakhir belum disave ke list form data
		//Jumlahnya antara jumlah pos dan ukuran data beda
		if(data.size()!=view.getPosCount()){
			setData();
			List<String> errors=this.data.validate();
			if(errors!=null){
				String textError="Silahkan terlebih dahulu koreksi Error berikut : </br>";
				for(String error:errors){
					textError=textError+error+"</br>";
				}
				view.showError(ErrorLabel.GENERAL, textError);
			}else{
				data.add(this.data);				
			}
		}
//		/simpan
		String status=model.insertData(data); //insert data
		if(status!=null){ //penyimpanan gagal
			view.showError(ErrorLabel.GENERAL, status);
		}else{ //penyimpanan sukses
			Collection<Window> list=view.getUI().getWindows();
			for(Window w:list){
				view.getUI().removeWindow(w);
			}
			view.resetView();
			view.resetForm();
			Notification.show("Penyimpanan rencana kebutuhan berhasil", Type.TRAY_NOTIFICATION);
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
	
	//untuk form edit, ketika check box is ppn diklik
	public void changePrice(boolean isPPN, int idReqPlanning){
		ReqPlanning req=model.getSingleReqPlanning(idReqPlanning);
		if(req!=null){
			if(isPPN){
				view.getInputPrice().setValue(String.valueOf(req.getPriceEstimationPPN()));
			}else{
				view.getInputPrice().setValue(String.valueOf(req.getPriceEstimation()));
				
			}			
		}
	}
	
}
