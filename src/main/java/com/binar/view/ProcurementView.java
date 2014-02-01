package com.binar.view;

import com.binar.core.procurement.Invoice;
import com.binar.core.procurement.PurchaseOrder;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;

public class ProcurementView extends CustomComponent implements View {

	Label label=new Label("Procurement Management ");
	TabSheet tabSheet=new TabSheet();
	Invoice invoice;
	PurchaseOrder purchaseOrder;
	GeneralFunction function;
	
	@Override
	public void enter(ViewChangeEvent event) {
		function=new GeneralFunction();
		invoice=new Invoice(function);
		purchaseOrder = new PurchaseOrder(function);
		tabSheet.addTab(purchaseOrder).setCaption("Surat Pesanan");
		tabSheet.addTab(invoice).setCaption("Faktur");
		tabSheet.setSizeFull();
		this.setCompositionRoot(tabSheet);
		
		Navigator navigator=UI.getCurrent().getNavigator();
		String parameter=event.getParameters();
		if(parameter.equals(function.VIEW_PROCUREMENT_INVOICE)){
			tabSheet.setSelectedTab(invoice);
		}else if(parameter.equals(function.VIEW_PROCUREMENT_PURCHASE)){
			tabSheet.setSelectedTab(purchaseOrder);
		}
		this.setCompositionRoot(tabSheet);

		
	}

}
