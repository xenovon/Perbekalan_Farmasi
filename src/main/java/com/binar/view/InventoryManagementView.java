package com.binar.view;

import com.binar.core.inventoryManagement.ConsumptionList;
import com.binar.core.inventoryManagement.DeletionList;
import com.binar.core.inventoryManagement.ReceptionList;
import com.binar.core.inventoryManagement.StockList;
import com.binar.core.inventoryManagement.deletionList.DeletionApproval;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;

public class InventoryManagementView extends CustomComponent implements View, SelectedTabChangeListener {

	Label label=new Label("Inventory Management ");
	TabSheet tabSheet;
	GeneralFunction generalFunction;
	ConsumptionList consumption;
	ReceptionList receipt;
	DeletionList deletion;
	StockList stock;
	DeletionApproval deletionApproval;
	
	@Override
	public void enter(ViewChangeEvent event) {	
		generalFunction =new GeneralFunction();
		
		if(consumption==null){
			consumption=new ConsumptionList(generalFunction);			
		} if(deletion==null){
			deletion=new DeletionList(generalFunction);			
		} if(receipt==null){
			receipt=new ReceptionList(generalFunction);			
		} if(stock==null){
			stock=new StockList(generalFunction);			
		}if(deletionApproval==null){
			deletionApproval=new DeletionApproval(generalFunction);			
		}
		
		tabSheet=new TabSheet();
		tabSheet.addTab(consumption).setCaption("Daftar Pengeluaran Harian");
		tabSheet.addTab(receipt).setCaption("Daftar Penerimaan");
		tabSheet.addTab(stock).setCaption("Stok Gudang Farmasi");
		tabSheet.addTab(deletion).setCaption("Pengajuan Barang Kadaluarsa");
		tabSheet.addTab(deletionApproval).setCaption("Persetujuan Barang Kadaluarsa");
		
		tabSheet.addSelectedTabChangeListener(this);
		tabSheet.setSizeFull();
		
		Navigator navigator=UI.getCurrent().getNavigator();
		String parameter=event.getParameters();
		if(parameter.equals(generalFunction.VIEW_INVENTORY_CONSUMPTION)){
			tabSheet.setSelectedTab(consumption);
		}else if(parameter.equals(generalFunction.VIEW_INVENTORY_DELETION)){
			tabSheet.setSelectedTab(deletion);
		}else if(parameter.equals(generalFunction.VIEW_INVENTORY_DELETION_APPROVAL)){
			tabSheet.setSelectedTab(deletionApproval);
		}else if(parameter.equals(generalFunction.VIEW_INVENTORY_RECEPTION)){
			tabSheet.setSelectedTab(receipt);
		}else if(parameter.equals(generalFunction.VIEW_INVENTORY_STOCK)){
			tabSheet.setSelectedTab(stock);
		}
		
		this.setCompositionRoot(tabSheet);		
	}

	@Override
	public void selectedTabChange(SelectedTabChangeEvent event) {
		if(event.getTabSheet().getSelectedTab()==consumption){
			consumption.getPresenter().updateTable(consumption.getView().getSelectedPeriod());
		}
		if(event.getTabSheet().getSelectedTab()==receipt){
			receipt.getPresenter().updateTable(receipt.getView().getSelectedPeriod());
		}
		if(event.getTabSheet().getSelectedTab()==deletion){
			deletion.getPresenter().updateTable();
		}
		if(event.getTabSheet().getSelectedTab()==stock){
			stock.getPresenter().goodsChange();
		}
		if(event.getTabSheet().getSelectedTab()==deletionApproval){
			deletionApproval.getPresenter().updateTable();
		}
	}

}
