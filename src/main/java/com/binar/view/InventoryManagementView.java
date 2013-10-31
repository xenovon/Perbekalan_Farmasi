package com.binar.view;

import com.binar.core.inventoryManagement.Consumption;
import com.binar.core.inventoryManagement.Receipt;
import com.binar.core.inventoryManagement.Stock;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;

public class InventoryManagementView extends CustomComponent implements View {

	Label label=new Label("Inventory Management ");
	Consumption consumption =new Consumption();
	Receipt receipt =new Receipt();
	Stock stock=new Stock();
	
	TabSheet tabSheet=new TabSheet();
	@Override
	public void enter(ViewChangeEvent event) {
		this.setCompositionRoot(tabSheet);
		tabSheet.addComponents(receipt,consumption,stock);
		tabSheet.setSizeFull();
		
	}

}
