package com.binar.core.procurement.invoice.editInvoice;

import java.util.Collection;
import java.util.List;

import com.binar.core.procurement.invoice.editInvoice.EditInvoiceView.EditInvoiceListener;
import com.binar.core.procurement.invoice.newInvoice.NewInvoiceView.FormData;
import com.binar.entity.Invoice;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;

public class EditInvoicePresenter implements EditInvoiceListener {

	GeneralFunction function;
	EditInvoiceModel model;
	EditInvoiceViewImpl view;
	GetSetting setting;
	
	public EditInvoicePresenter(GeneralFunction function, EditInvoiceModel model, EditInvoiceViewImpl view){
		this.function=function;
		this.setting=function.getSetting();
		this.view=view;
		this.model=model;
		this.view.init();
		this.view.setListener(this);
	}
	public void buttonClick(String button) {
		if(button.equals("buttonCancel")){
			buttonCancel();
		}else if(button.equals("buttonSubmit")){
			submit();
		}
	}
	private void buttonCancel(){
		function.showDialog("Batalkan", 
				"Yakin Akan Membatalkan Mengubah Surat Pesanan?",
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
		}
	}
	
	private void submit(){
		view.hideError();
		FormData data=view.getEditedInvoice();
		List<String> results=model.saveInvoice(data);
		if(results==null){
			closeWindow();
			Notification.show("Perubahan Disimpan", Type.TRAY_NOTIFICATION);
		}else{
			String error="Tidak dapat menyimpan perubahan, perbaiki error berikut</br>";
			for(String x:results){
				error=error+"</br>"+x;
			}
			view.showError(error);
			return;
		}
		
	}
	@Override
	public void valueChange() {
		view.hideError();
	}
	
	public void generateInvoice(int idInvoice){
		Invoice data=model.getInvoice(idInvoice);
		view.generateInvoiceView(data);
	}


	@Override
	public double countPrice(boolean ppn, String quantity, String price,
			String discount) {
		try{
			int quantityD=Integer.parseInt(quantity);
			double priceD=Double.parseDouble(price);
			double discountD=Double.parseDouble(discount);
			if(!ppn){
				priceD=priceD+(priceD*setting.getPPN());
			}
			return model.getNewInvoiceModel().countTotalPrice(quantityD, priceD, discountD);
		}catch(Exception e){
			return 0;
		}

	}

}
