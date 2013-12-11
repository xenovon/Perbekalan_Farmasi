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
		this.view.init();
		this.view.setComboUserData(model.getUserComboData());
		this.date=function.getDate();
	}
	@Override
	public void buttonClick(String button) {
		if(button.equals("buttonNext")){
			List<PurchaseOrder> purchaseOrders=model.generatePurchaseOrder(view.getFormData());
			boolean  success=view.generatePurchaseOrderListView(purchaseOrders);
			if(success){
				view.setFormView(FormViewEnum.VIEW_PO);				
			}else{
				Notification.show("Terjadi kesalahan", Type.ERROR_MESSAGE);
			}
		}else if(button.equals("buttonBack")){
			view.setFormView(FormViewEnum.VIEW_REQPLANNING);
		}else if(button.equals("buttonCreate")){
			List<PurchaseOrder> data=view.getPurchaseOrderListData();
			String result=model.savePurchaseOrder(data);
			if(result==null){
				Notification.show("Penyimpanan Kebutuhan berhasil", Type.TRAY_NOTIFICATION);
				closeWindow();
			}else{
				Notification.show("Penyimpanan Gagal", Type.ERROR_MESSAGE);
			}
		}else if(button.equals("buttonCancel")){
			buttonCancel();
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
		DateTime period=date.parseDateMonth(view.getSelectedPeriod());
		List<ReqPlanning> reqPlannings=model.getReqPlanning(period);
		view.generateReqPlanningTable(reqPlannings);
	}
	
	
	
}
