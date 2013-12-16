package com.binar.core.procurement.purchaseOrder.editPurchaseOrder;

import java.util.Collection;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.core.procurement.purchaseOrder.editPurchaseOrder.EditPurchaseOrderView.EditPurchaseOrderListener;
import com.binar.core.procurement.purchaseOrder.newPurchaseOrder.NewPurchaseOrderView.NewPurchaseOrderListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;

public class EditPurchaseOrderPresenter implements EditPurchaseOrderListener {

	private GeneralFunction function;
	private EditPurchaseOrderModel model;
	private EditPurchaseOrderViewImpl view;
	public EditPurchaseOrderPresenter(EditPurchaseOrderModel model, 
			EditPurchaseOrderViewImpl view, GeneralFunction function) {
		this.function=function;
		this.model=model;
		this.view=view;
		view.setListener(this);
		view.init();
		view.setComboBoxData(model.getUserComboData());
	}
	@Override
	public void buttonClick(String button) {
		if(button.equals("cancel")){
			buttonCancel();
		}else if(button.equals("submit")){
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
		FormData data=view.getFormData();
		List<String> results=data.validate();
		if(results!=null){
			String error="Tidak dapat menyimpan perubahan, perbaiki error berikut</br>";
			for(String x:results){
				error=error+"</br>"+x;
			}
			view.showError(error);
			return;
		}
		String result=model.updatePurchaseOrder(data);
		if(result==null){
			closeWindow();
			Notification.show("Perubahan Disimpan", Type.TRAY_NOTIFICATION);
		}else{
			view.showError(result);
		}
		
	}
	@Override
	public void valueChange() {
		view.hideError();
	}
	
	public void setFormData(int idPurchase){
		view.setFormData(model.getPurchaseOrder(idPurchase));
	}
}
