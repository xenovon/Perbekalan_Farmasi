package com.binar.core.procurement.invoice.newInvoice;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.binar.core.procurement.invoice.newInvoice.NewInvoiceView.FormData;
import com.binar.core.procurement.invoice.newInvoice.NewInvoiceView.FormViewEnum;
import com.binar.core.procurement.invoice.newInvoice.NewInvoiceView.NewInvoiceListener;
import com.binar.entity.Invoice;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;

public class NewInvoicePresenter implements NewInvoiceListener{

	GeneralFunction function;
	NewInvoiceModel model;
	NewInvoiceViewImpl view;
	DateManipulator date;
	GetSetting setting;
	public NewInvoicePresenter(NewInvoiceModel model, NewInvoiceViewImpl view, GeneralFunction function) {
		this.model=model;
		this.view=view;
		this.function=function;
		this.view.setListener(this);
		this.view.init();
		this.date=function.getDate();
		this.periodChange();
		this.setting=function.getSetting();
		
	}
	@Override
	public void buttonClick(String button) {
		view.hideError();
		if(button.equals("buttonNext")){
			buttonNext();
			System.out.println("Next Click");
		}else if(button.equals("buttonBack")){
			buttonBack();
		}else if(button.equals("buttonCreate")){
			buttonCreate();
		}else if(button.equals("buttonCancel")){
			buttonCancel();
		}
	}
	private void buttonCreate(){
		FormData data=view.getEditedInvoice();
		List<String> error=model.saveInvoice(data);
		if(error==null){
			closeWindow();	
			Notification.show("Data faktur disimpan", Type.TRAY_NOTIFICATION);
		}else{
			String stringError="Tidak dapat menyimpan Faktur, kesalahan : </br>";
			for(String x:error){
				stringError=stringError+"</br>"+x;
			}
			view.showError(stringError);
		}
	}
	private void buttonNext(){
		int purchaseOrder=view.getPurchaseOrderSelect();
		if(purchaseOrder!=0){
			PurchaseOrder order=model.getPurchaseOrder(purchaseOrder);
			view.setFormView(FormViewEnum.EDIT_VIEW);
			view.generateInvoiceView(order);
			System.out.println("Next Click "+purchaseOrder);			
		}

	}
	private void buttonCancel(){
		function.showDialog("Batalkan", 
				"Yakin Akan Membatalkan Membuat Faktur Baru?",
				new ClickListener() {
					public void buttonClick(ClickEvent event) {
						closeWindow();
					}
				}, view.getUI());
		
	}
	public void buttonBack(){
		function.showDialog("Kembali", 
				"Kembali ke menu sebelumnya? Data faktur yang belum tersimpan akan dihapus",
				new ClickListener() {
					public void buttonClick(ClickEvent event) {
						view.resetForm();
						view.setFormView(FormViewEnum.INPUT_VIEW);
					}
				}, view.getUI());
		
	}
	public void closeWindow(){
		Collection<Window> list=view.getUI().getWindows();
		for(Window w:list){
			view.getUI().removeWindow(w);
		}
	}

	@Override
	public void periodChange() {
		Date dateStart=view.getRangeStart();
		Date dateEnd=view.getRangeEnd();
		if(dateStart!=null && dateEnd!=null){
			List<PurchaseOrder> orders=model.getPurchaseOrderList(dateStart, dateEnd);
			if(orders==null){
				Notification.show("Data faktur kosong", Type.WARNING_MESSAGE);
			}else{
				Map<Integer, String> data=new HashMap<Integer, String>();
				for(PurchaseOrder order:orders){
					data.put(order.getIdPurchaseOrder(), order.getPurchaseOrderName());
				}
				view.setComboPurchaseOrder(data);				
			}
		}
		
	}
	//	public double countTotalPrice(int quantity, double pricePPN, double discount){

	@Override
	public double countPrice(boolean ppn, String quantity, String price, String discount) {
		try{
			int quantityD=Integer.parseInt(quantity);
			double priceD=Double.parseDouble(price);
			double discountD=Double.parseDouble(discount);
			if(!ppn){
				priceD=priceD+(priceD*setting.getPPN());
			}
			return model.countTotalPrice(quantityD, priceD, discountD);
		}catch(Exception e){
			return 0;
		}
	}
	
}
