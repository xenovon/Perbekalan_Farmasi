package com.binar.core.procurement.invoice;

import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;

import com.binar.core.procurement.invoice.InvoiceView.InvoiceListener;
import com.binar.entity.Invoice;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class InvoicePresenter implements InvoiceListener{
	
	InvoiceModel model;
	InvoicePresenter presenter;
	InvoiceViewImpl view;
	GeneralFunction function;
	DateManipulator date;
	public InvoicePresenter(InvoiceViewImpl view, InvoiceModel model, GeneralFunction function) {
		this.function=function;
		this.model=model;
		this.view=view;
		this.date=function.getDate();
	}
	@Override
	public void buttonClick(String buttonName) {
		if(buttonName.equals("buttonNewInvoice")){
			if(null==null){
			}
			view.getUI().addWindow(null);
			addWIndowCloseListener();
		}
		
	}
	@Override
	public void editClick(int idInvoice) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteClick(int idInvoice) {
		final int finalIdInvoice=idInvoice;
		Invoice invoice=model.getInvoice(idInvoice);
		function.showDialog("Hapus Data", "Yakin akan menghapus faktur "+invoice.getInvoiceName(),
				new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						String result=model.deleteInvoice(finalIdInvoice);
						if(result!=null){
							Notification.show(result, Type.ERROR_MESSAGE);
						}else{
							updateTable();
							Notification.show("Data sukses dihapus");
						}
					}
				}, view.getUI());
		
	}
	@Override
	public void showClick(int idInvoice) {
		Invoice invoice=model.getInvoice(idInvoice);
		if(invoice!=null){
			view.showDetailWindow(invoice);
			addWIndowCloseListener();
		}
		
	}
	@Override
	public void valueChange(String value) {
		updateTable();
		
	}
	@Override
	public void updateTable() {
		String period=view.getSelectedPeriod();
		System.out.println(" Periode "+period);
		DateTime date=this.date.parseDateMonth(period);
		DateTime year=null;
		DateTime month=null;
		//untuk menampung opsi pilihan "Semua Bulan"
		if(date==null){
			year=DateTime.now().withYear(Integer.parseInt(period.split("-")[1]));	
		}else{
			year=date;
			month=date;
		}
		List<Invoice> data=model.getInvoiceList(month, year);
		if(data!=null){
			view.updateTableData(data);			
		}else{
			Notification.show("Terjadi kesalahan pengambilan data");
		}
		
	}
	public void addWIndowCloseListener(){
		Collection<Window> windows=view.getUI().getWindows();
		for(Window window:windows){
			window.addCloseListener(new CloseListener() {
				public void windowClose(CloseEvent e) {
					updateTable();
				}
			});
		}
	}

}
