package com.binar.core.procurement.purchaseOrder;

import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;

import com.binar.core.dataManagement.manufacturerManagement.inputManufacturer.InputManufacturerModel;
import com.binar.core.dataManagement.manufacturerManagement.inputManufacturer.InputManufacturerPresenter;
import com.binar.core.dataManagement.manufacturerManagement.inputManufacturer.InputManufacturerViewImpl;
import com.binar.core.procurement.purchaseOrder.PurchaseOrderView.PurchaseOrderListener;
import com.binar.core.procurement.purchaseOrder.editPurchaseOrder.EditPurchaseOrderModel;
import com.binar.core.procurement.purchaseOrder.editPurchaseOrder.EditPurchaseOrderPresenter;
import com.binar.core.procurement.purchaseOrder.editPurchaseOrder.EditPurchaseOrderViewImpl;
import com.binar.core.procurement.purchaseOrder.newPurchaseOrder.NewPurchaseOrderModel;
import com.binar.core.procurement.purchaseOrder.newPurchaseOrder.NewPurchaseOrderPresenter;
import com.binar.core.procurement.purchaseOrder.newPurchaseOrder.NewPurchaseOrderViewImpl;
import com.binar.core.procurement.purchaseOrder.printPurchaseOrder.GeneralPrint;
import com.binar.entity.PurchaseOrder;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class PurchaseOrderPresenter implements PurchaseOrderListener {

	PurchaseOrderModel model;
	PurchaseOrderViewImpl view;
	GeneralFunction function;
	DateManipulator date;
	
	NewPurchaseOrderModel modelNew;
	NewPurchaseOrderPresenter presenterNew;
	NewPurchaseOrderViewImpl viewNew;
	
	EditPurchaseOrderModel modelEdit;
	EditPurchaseOrderPresenter presenterEdit;
	EditPurchaseOrderViewImpl viewEdit;
	
	public PurchaseOrderPresenter(PurchaseOrderViewImpl view, PurchaseOrderModel model, GeneralFunction function) {
		this.function=function;
		this.date=function.getDate();
		this.model=model;
		this.view=view;
		
		this.view.setListener(this);
		this.view.init();
		printClick();
		
	}
	@Override
	public void buttonClick(String buttonName) {
		if(buttonName.equals("buttonNewPurchase")){
			if(presenterNew==null){
				modelNew=new NewPurchaseOrderModel(function);
				viewNew=new NewPurchaseOrderViewImpl(function);
				presenterNew=new NewPurchaseOrderPresenter(modelNew, viewNew, function);
			}
			view.getUI().addWindow(viewNew);
			addWIndowCloseListener();
		}else if(buttonName.equals("buttonPrint")){
		}else if(buttonName.equals("buttonSave")){
			
		}
	}
	@Override
	public void deleteClick(int idPurchaseOrder) {
		final int finalIdPurchase=idPurchaseOrder;
		PurchaseOrder order=model.getPurchaseOrder(idPurchaseOrder);
		function.showDialog("Hapus Data", "Yakin akan menghapus surat pesanan "+order.getPurchaseOrderName(),
				new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						String result=model.deletePurchaseOrder(finalIdPurchase);
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
	public void showClick(int idPurchaseOrder) {
		PurchaseOrder order=model.getPurchaseOrder(idPurchaseOrder);
		if(order!=null){
			view.showDetailWindow(order);
			addWIndowCloseListener();
		}
	}
	@Override
	public void valueChange(String value) {
		updateTable();
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
	@Override
	public void updateTable() {
		
		String period=view.getSelectedPeriod();
		System.out.println(" Periode "+period);
		DateTime date;
		try {
			date = this.date.parseDateMonth(period);
		} catch (Exception e) {
			date=null;
//			e.printStackTrace();
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
		List<PurchaseOrder> data=model.getPurchaseOrderList(month, year);
		if(data!=null){
			view.updateTableData(data);			
		}else{
//			Notification.show("Terjadi kesalahan pengambilan data");
		}
	}

	public void editClick(int idPurchase) {
		if(presenterEdit==null){
			modelEdit =new EditPurchaseOrderModel(function);
			viewEdit = new EditPurchaseOrderViewImpl(function);
			presenterEdit=new EditPurchaseOrderPresenter(modelEdit, viewEdit, function);
		}
		viewEdit.resetForm();	
		presenterEdit.setFormData(idPurchase);
		view.displayForm(viewEdit, "Ubah Data Surat Pesanan", "65%");
		addWIndowCloseListener();
	}
	
	public void printClick(){
		// Create an opener extension
		BrowserWindowOpener opener =
		new BrowserWindowOpener(GeneralPrint.class);
		opener.setFeatures("height=200,width=400,resizable");
		// A button to open the printer-friendly page.
		opener.setParameter("anu","Gahahahaha");
		opener.extend(view.getButtonPrint());
	}
	
	public void saveClick(){
		Notification.show("Print pdf");
	}


}
