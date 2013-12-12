package com.binar.core.procurement.purchaseOrder;

import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;

import com.binar.core.procurement.purchaseOrder.PurchaseOrderView.PurchaseOrderListener;
import com.binar.core.procurement.purchaseOrder.newPurchaseOrder.NewPurchaseOrderModel;
import com.binar.core.procurement.purchaseOrder.newPurchaseOrder.NewPurchaseOrderPresenter;
import com.binar.core.procurement.purchaseOrder.newPurchaseOrder.NewPurchaseOrderViewImpl;
import com.binar.entity.PurchaseOrder;
import com.binar.generalFunction.GeneralFunction;
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
	NewPurchaseOrderModel modelNew;
	NewPurchaseOrderPresenter presenterNew;
	NewPurchaseOrderViewImpl viewNew;
	public PurchaseOrderPresenter(PurchaseOrderViewImpl view, PurchaseOrderModel model, GeneralFunction function) {
		this.function=function;
		this.model=model;
		this.view=view;
		this.view.setListener(this);
		this.view.init();
		
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
		}
	}
	@Override
	public void editClick(int idPurchaseOrder) {
		Notification.show("edit Surat Pesanan"+idPurchaseOrder);
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
		DateTime year=DateTime.now().withYear(Integer.parseInt(period.split("-")[1]));
		DateTime month=DateTime.now().withMonthOfYear(Integer.parseInt(period.split("-")[0]));
		List<PurchaseOrder> data=model.getPurchaseOrderList(month, year);
		if(data!=null){
			view.updateTableData(data);			
		}else{
			Notification.show("Terjadi kesalahan pengambilan data");
		}
	}


}
