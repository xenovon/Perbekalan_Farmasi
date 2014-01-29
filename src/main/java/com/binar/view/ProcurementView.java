package com.binar.view;

import com.binar.core.procurement.Invoice;
import com.binar.core.procurement.PurchaseOrder;
import com.binar.core.requirementPlanning.Approval;
import com.binar.core.requirementPlanning.Forecast;
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
import com.vaadin.ui.UI;

public class ProcurementView extends CustomComponent implements View {

	Label label=new Label("Procurement Management ");
	TabSheet tabSheet=new TabSheet();
	Invoice invoice;
	PurchaseOrder purchaseOrder;
	GeneralFunction function;
	LoginManager loginManager;
	
	@Override
	public void enter(ViewChangeEvent event) {
		function=new GeneralFunction();
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
	
	private void generateFarmationView(ViewChangeEvent event){
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
	private void generateIFRSView(ViewChangeEvent event){
		generateFarmationView(event);
	}
	
	private  void generateSupportView(ViewChangeEvent event){
		purchaseOrder = new PurchaseOrder(function);
		tabSheet.addTab(purchaseOrder).setCaption("Surat Pesanan");
		tabSheet.setSizeFull();
		this.setCompositionRoot(tabSheet);
		
		String parameter=event.getParameters();
		if(parameter.equals(function.VIEW_PROCUREMENT_PURCHASE)){
			tabSheet.setSelectedTab(purchaseOrder);
		}
		this.setCompositionRoot(tabSheet);
	
	}

	private  void generatePPKView(ViewChangeEvent event){
		generateSupportView(event);
	}

	private void generateProcurementView(ViewChangeEvent event){
		generateFarmationView(event);
	}

	private void generateAdminView(ViewChangeEvent event){
		this.setCompositionRoot(new Label("Error 404: Halaman tidak ditemukan"));
	}
}
