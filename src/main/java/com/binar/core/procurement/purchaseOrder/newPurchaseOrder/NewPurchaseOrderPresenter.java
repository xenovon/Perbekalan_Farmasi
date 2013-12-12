package com.binar.core.procurement.purchaseOrder.newPurchaseOrder;

import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;

import com.binar.core.procurement.purchaseOrder.newPurchaseOrder.NewPurchaseOrderView.FormData;
import com.binar.core.procurement.purchaseOrder.newPurchaseOrder.NewPurchaseOrderView.FormViewEnum;
import com.binar.core.procurement.purchaseOrder.newPurchaseOrder.NewPurchaseOrderView.NewPurchaseOrderListener;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;

public class NewPurchaseOrderPresenter implements NewPurchaseOrderListener {

	private GeneralFunction function;
	private NewPurchaseOrderModel model;
	private NewPurchaseOrderViewImpl view;
	private DateManipulator date;
	public NewPurchaseOrderPresenter(NewPurchaseOrderModel model, 
			NewPurchaseOrderViewImpl view, GeneralFunction function) {
		this.model=model;
		this.view=view;
		this.function=function;
		this.date=function.getDate();
		this.view.setListener(this);
		this.view.init();
		this.view.setComboUserData(model.getUserComboData());
		this.date=function.getDate();
		this.periodChange();
	}
	@Override
	public void buttonClick(String button) {
		view.getLabelError().setVisible(false);
		if(button.equals("buttonNext")){
			buttonNext();
		}else if(button.equals("buttonBack")){
			view.setFormView(FormViewEnum.VIEW_REQPLANNING);
		}else if(button.equals("buttonCreate")){
			List<PurchaseOrder> data=view.getPurchaseOrderListData();
			String result=model.savePurchaseOrder(data);
			if(result==null){
				Notification.show("Penyimpanan Surat Pesanan Berhasil", Type.TRAY_NOTIFICATION);
			}else{
				Notification.show("Penyimpanan Gagal", Type.ERROR_MESSAGE);
			}
			view.setFormView(FormViewEnum.VIEW_REQPLANNING);
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
		FormData formData=view.getFormData();
		List<String> error=formData.validateFormData();
		if(error==null){
			List<PurchaseOrder> purchaseOrders=model.generatePurchaseOrder(view.getFormData());
			boolean  success=view.generatePurchaseOrderListView(purchaseOrders);
			if(success){
				System.err.println("Sukses" +purchaseOrders.size());
				view.setFormView(FormViewEnum.VIEW_PO);
				view.generatePurchaseOrderListView(purchaseOrders);
			}else{
				Notification.show("Terjadi kesalahan", Type.ERROR_MESSAGE);
			}			
		}else{
			String textError="Tidak dapat melanjutkan, perbaiki error berikut : </br> ";
			
			for(String err:error){
				textError=textError+err+"</br>";
			}
			view.getLabelError().setVisible(true);
			view.getLabelError().setValue(textError);
		}
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
		view.getLabelError().setVisible(false);
		
		DateTime period=date.parseDateMonth(view.getSelectedPeriod());
		List<ReqPlanning> reqPlannings=model.getReqPlanning(period);
		if(reqPlannings!=null){
			System.out.println("Period change "+reqPlannings.size());
			if(reqPlannings.size()==0){
				Notification.show("Data kosong");
				view.getTableContainer().removeAllItems();
				view.getButtonNext().setEnabled(false);
			}else{
				view.generateReqPlanningTable(reqPlannings);
				view.getButtonNext().setEnabled(true);
			}
		}
		
	}
	
	
	
}
