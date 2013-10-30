package com.binar.view;

import com.binar.core.dataManagement.GoodsManagement;
import com.binar.core.dataManagement.ProducerManagement;
import com.binar.core.dataManagement.SupplierManagement;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;

public class DataManagementView extends CustomComponent implements View {

	GoodsManagement goodsManagement=new GoodsManagement();
	ProducerManagement producerManagement=new ProducerManagement();
	SupplierManagement supplierManagement=new SupplierManagement();
	
	TabSheet tabSheet=new TabSheet();
		@Override
	public void enter(ViewChangeEvent event) {
		tabSheet.addTab(goodsManagement).setCaption("Manajemen Barang");
		tabSheet.addTab(supplierManagement);
		tabSheet.addTab(producerManagement);
		tabSheet.setSizeFull();

		setCompositionRoot(tabSheet);
	}

}
