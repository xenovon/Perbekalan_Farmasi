package com.binar.core.procurement.invoice;

import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;

import com.binar.core.procurement.invoice.InvoiceView.InvoiceListener;
import com.binar.core.procurement.invoice.editInvoice.EditInvoiceModel;
import com.binar.core.procurement.invoice.editInvoice.EditInvoicePresenter;
import com.binar.core.procurement.invoice.editInvoice.EditInvoiceViewImpl;
import com.binar.core.procurement.invoice.newInvoice.NewInvoiceModel;
import com.binar.core.procurement.invoice.newInvoice.NewInvoicePresenter;
import com.binar.core.procurement.invoice.newInvoice.NewInvoiceViewImpl;
import com.binar.core.procurement.purchaseOrder.editPurchaseOrder.EditPurchaseOrderModel;
import com.binar.core.procurement.purchaseOrder.editPurchaseOrder.EditPurchaseOrderPresenter;
import com.binar.core.procurement.purchaseOrder.editPurchaseOrder.EditPurchaseOrderViewImpl;
import com.binar.core.procurement.purchaseOrder.newPurchaseOrder.NewPurchaseOrderModel;
import com.binar.core.procurement.purchaseOrder.newPurchaseOrder.NewPurchaseOrderPresenter;
import com.binar.core.procurement.purchaseOrder.newPurchaseOrder.NewPurchaseOrderViewImpl;
import com.binar.entity.Invoice;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.LoginManager;
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
	LoginManager loginManager;

	NewInvoiceModel modelNew;
	NewInvoicePresenter presenterNew;
	NewInvoiceViewImpl viewNew;
	
	EditInvoiceModel modelEdit;
	EditInvoiceViewImpl viewEdit;
	EditInvoicePresenter presenterEdit;
	
	public InvoicePresenter(InvoiceViewImpl view, InvoiceModel model, GeneralFunction function) {
		this.function=function;
		this.model=model;
		this.view=view;
		this.date=function.getDate();
		this.loginManager=function.getLogin();

		this.view.setListener(this);
		this.view.init();
		roleProcessor();
		this.updateTable();
	
	}
	/*
	 *  Manajemen ROLE level Fungsionalitas
	 *   
	 */
	boolean withEditInvoice = false;
	public void roleProcessor(){
		String role=loginManager.getRoleId();
		if(!role.equals(loginManager.FRM)){
			view.hideButtonNew();
			withEditInvoice=false;
		}else{
			view.showButtonNew();
			withEditInvoice=true;
		}
		
	}

	
	@Override
	public void buttonClick(String buttonName) {
		if(buttonName.equals("buttonNewInvoice")){
			if(presenterNew==null){
				modelNew=new NewInvoiceModel(function);
				viewNew=new NewInvoiceViewImpl(function);
				presenterNew=new NewInvoicePresenter(modelNew, viewNew, function);
			}
			view.getUI().addWindow(viewNew);
			addWIndowCloseListener();
		}
	}
	@Override
	public void editClick(int idInvoice) {
		if(presenterEdit==null){
			modelEdit =new EditInvoiceModel(function);
			viewEdit = new EditInvoiceViewImpl(function);
			presenterEdit=new EditInvoicePresenter(function,modelEdit, viewEdit);
		}
		presenterEdit.generateInvoice(idInvoice);
		view.displayForm(viewEdit, "Ubah Data Faktur", "65%");
		addWIndowCloseListener();
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
		DateTime date;
		try {
			date = this.date.parseDateMonth(period);
		} catch (Exception e) {
			date=null;
		}
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
			view.updateTableData(data, withEditInvoice);			
		}else{
			Notification.show("Terjadi kesalahan pengambilan data");
		}
		
	}
	public double getPercentage(Invoice invoice){
		return model.getPercentage(invoice);
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
