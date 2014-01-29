package com.binar.view;

import com.binar.core.dataManagement.GoodsManagement;
import com.binar.core.dataManagement.ManufacturerManagement;
import com.binar.core.dataManagement.SupplierManagement;
import com.binar.core.inventoryManagement.ConsumptionList;
import com.binar.core.inventoryManagement.DeletionList;
import com.binar.core.inventoryManagement.ReceptionList;
import com.binar.core.inventoryManagement.StockList;
import com.binar.core.inventoryManagement.deletionList.DeletionApproval;
import com.binar.core.requirementPlanning.Approval;
import com.binar.core.requirementPlanning.InputRequirementPlanning;
import com.binar.core.requirementPlanning.ReqPlanningList;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.LoginManager;
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
	LoginManager loginManager;

	TabSheet tabSheet;
		@Override
	public void enter(ViewChangeEvent event) {
		
		tabSheet=new TabSheet();
		
		if(function==null){
			function=new GeneralFunction();
		}
		this.loginManager=function.getLogin();
		String userLoginRole=loginManager.getRoleId();
		
		/*
		 * Manajemen Role untuk Level VIEW
		 * 
		 */
		if(loginManager.getRoleId().equals(loginManager.FRM)){
			generateFarmationView(event);
		}else if(loginManager.getRoleId().equals(loginManager.IFRS)){
			generateIFRSView(event);
		}else if(loginManager.getRoleId().equals(loginManager.PNJ)){
			generateSupportView(event);
		}else if(loginManager.getRoleId().equals(loginManager.PPK)){
			generatePPKView(event);
		}else if(loginManager.getRoleId().equals(loginManager.TPN)){
			generateProcurementView(event);
		}else if(loginManager.getRoleId().equals(loginManager.ADM)){
			generateAdminView(event);
		}
		
		
	}
		@Override
		public void selectedTabChange(SelectedTabChangeEvent event) {
			if(event.getTabSheet().getSelectedTab()==goodsManagement){
				
			}else if(event.getTabSheet().getSelectedTab()==supplierManagement){
				
			}else if(event.getTabSheet().getSelectedTab()==manufacturerManagement){
				
			}
			
		}
		
		
		private void generateFarmationView(ViewChangeEvent event){
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
			String parameter=event.getParameters();
			if(parameter.equals(function.VIEW_GOODS_MANAGEMENT)){
				tabSheet.setSelectedTab(goodsManagement);
			}else if(parameter.equals(function.VIEW_SUPPLIER_MANAGEMENT)){
				tabSheet.setSelectedTab(supplierManagement);
			}
			
			
		}
		private void generateIFRSView(ViewChangeEvent event){
			generateFarmationView(event);
		}

		private  void generateSupportView(ViewChangeEvent event){
			generatePPKView(event);
		}

		private  void generatePPKView(ViewChangeEvent event){
			if(goodsManagement==null){
				goodsManagement=new GoodsManagement(function);
			}
			
			
			tabSheet.addTab(goodsManagement).setCaption("Manajemen Barang");
			tabSheet.setSizeFull();
			tabSheet.addSelectedTabChangeListener(this);
			setCompositionRoot(tabSheet);
			System.out.println("Selesai enter");
			
			Navigator navigator=UI.getCurrent().getNavigator();
			String parameter=event.getParameters();
			if(parameter.equals(function.VIEW_GOODS_MANAGEMENT)){
				tabSheet.setSelectedTab(goodsManagement);
			}
			
		}

		private void generateProcurementView(ViewChangeEvent event){
			generateFarmationView(event);
		}

		private void generateAdminView(ViewChangeEvent event){
			this.setCompositionRoot(new Label("Error 404: Halaman tidak ditemukan"));
		}

}