package com.binar.view;

import com.binar.core.dataManagement.GoodsManagement;
import com.binar.core.dataManagement.ManufacturerManagement;
import com.binar.core.dataManagement.SupplierManagement;
import com.binar.core.requirementPlanning.Approval;
import com.binar.core.requirementPlanning.InputRequirementPlanning;
import com.binar.core.requirementPlanning.ReqPlanningList;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.UI;

public class DataManagementView extends CustomComponent implements View, SelectedTabChangeListener {

	GeneralFunction function;
	GoodsManagement goodsManagement;
	ManufacturerManagement manufacturerManagement;
	SupplierManagement supplierManagement;
	
	TabSheet tabSheet;
		@Override
	public void enter(ViewChangeEvent event) {
		
		if(tabSheet==null){
			tabSheet=new TabSheet();
		}
			
		if(function==null){
			function=new GeneralFunction();
		}
		if(goodsManagement==null){
			goodsManagement=new GoodsManagement(function);
		}
		if(manufacturerManagement==null){
			manufacturerManagement=new ManufacturerManagement(function);
		}
		if(supplierManagement==null){
			supplierManagement=new SupplierManagement(function);
		}
		
		tabSheet.addTab(goodsManagement).setCaption("Manajemen Barang");
		tabSheet.addTab(supplierManagement).setCaption("Manajemen Distributor");;
		tabSheet.addTab(manufacturerManagement).setCaption("Manajemen Produsen");;
		tabSheet.setSizeFull();
		tabSheet.addSelectedTabChangeListener(this);
		setCompositionRoot(tabSheet);
		System.out.println("Selesai enter");
		
		Navigator navigator=UI.getCurrent().getNavigator();
		
	}
		@Override
		public void selectedTabChange(SelectedTabChangeEvent event) {
			if(event.getTabSheet().getSelectedTab()==goodsManagement){
				
			}else if(event.getTabSheet().getSelectedTab()==supplierManagement){
				
			}else if(event.getTabSheet().getSelectedTab()==manufacturerManagement){
				
			}
			
		}
		
}