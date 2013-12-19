package com.binar.core.procurement.invoice.newInvoice;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.binar.core.procurement.invoice.newInvoice.NewInvoiceView.FormViewEnum;
import com.binar.core.procurement.invoice.newInvoice.NewInvoiceView.NewInvoiceListener;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
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
	public NewInvoicePresenter(NewInvoiceModel model, NewInvoiceViewImpl view, GeneralFunction function) {
		this.model=model;
		this.view=view;
		this.function=function;
		this.view.setListener(this);
		this.view.init();
		this.date=function.getDate();
		this.periodChange();
		
	}
	@Override
	public void buttonClick(String button) {
		view.getLabelError().setVisible(false);
		if(button.equals("buttonNext")){
			buttonNext();
		}else if(button.equals("buttonBack")){
			view.setFormView(FormViewEnum.INPUT_VIEW);
		}else if(button.equals("buttonCreate")){
			closeWindow();
		}else if(button.equals("buttonCancel")){
			buttonCancel();
		}else if(button.equals("buttonCheckAll")){
			view.checkReqPlanning(true);
		}else if(button.equals("buttonUncheckAll")){
			view.checkReqPlanning(false);
		}
	}
	private void buttonNext(){
	}
	private void buttonCancel(){
		function.showDialog("Batalkan", 
				"Yakin Akan Membatalkan Membuat Surat Pesanan?",
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

	@Override
	public void periodChange() {
		Date dateStart=view.getRangeStart();
		Date dateEnd=view.getRangeEnd();
		if(dateStart!=null && dateEnd!=null){
			List<PurchaseOrder> orders=model.getPurchaseOrderList(dateStart, dateEnd);
			Map<Integer, String> data=new HashMap<Integer, String>();
			for(PurchaseOrder order:orders){
				data.put(order.getIdPurchaseOrder(), order.getPurchaseOrderName());
			}
			view.setComboPurchaseOrder(data);
		}
		
	}
	
}
