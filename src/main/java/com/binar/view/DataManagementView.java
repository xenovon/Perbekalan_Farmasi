package com.binar.view;

import com.binar.core.dataManagement.GoodsManagement;
import com.binar.core.dataManagement.ProducerManagement;
import com.binar.core.dataManagement.SupplierManagement;
import com.binar.core.requirementPlanning.Approval;
import com.binar.core.requirementPlanning.InputRequirementPlanning;
import com.binar.core.requirementPlanning.ReqPlanningList;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;

public class DataManagementView extends CustomComponent implements View {

	GeneralFunction function;
	GoodsManagement goodsManagement;
	ProducerManagement producerManagement;
	SupplierManagement supplierManagement;
	
	TabSheet tabSheet=new TabSheet();
		@Override
	public void enter(ViewChangeEvent event) {
			
			
		tabSheet.addTab(goodsManagement).setCaption("Manajemen Barang");
		tabSheet.addTab(supplierManagement).setCaption("Manajemen Supplier");;
		tabSheet.addTab(producerManagement).setCaption("Manajemen Produsen");;
		tabSheet.setSizeFull();

		setCompositionRoot(tabSheet);
	}
/*
 * 		//Inisiasi General function		
		generalFunction =new GeneralFunction();
		
		approval=new Approval(generalFunction);
		inputReqPl=new InputRequirementPlanning(generalFunction);
		reqPlanning=new ReqPlanningList(generalFunction);
		
		
		tabSheet=new TabSheet();
		tabSheet.addTab(reqPlanning).setCaption("Daftar Rencana Kebutuhan");
		tabSheet.addTab(inputReqPl).setCaption("Input Rencana Kebutuhan");;
		tabSheet.addTab(approval).setCaption("Persetujuan");

		tabSheet.addSelectedTabChangeListener(this);
		tabSheet.setSizeFull();
		this.setCompositionRoot(tabSheet);
	}

	//untuk meload ulang (refresh) data tabel di tab ketika tab diakses
	@Override
	public void selectedTabChange(SelectedTabChangeEvent event) {
		if(event.getTabSheet().getSelectedTab()==reqPlanning){
			reqPlanning.getPresenter().updateTable(reqPlanning.getView().getSelectedPeriod());
		}
		if(event.getTabSheet().getSelectedTab()==inputReqPl){
			inputReqPl.getPresenter().updateTable(inputReqPl.getView().getPeriodeValue());
		}
		if(event.getTabSheet().getSelectedTab()==approval){
			approval.getPresenter().updateTable(approval.getView().getPeriodeValue());
		}
	}

 */
}
