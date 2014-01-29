package com.binar.core.inventoryManagement.receptionList;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.binar.core.inventoryManagement.consumptionList.inputConsumption.InputConsumptionModel;
import com.binar.core.inventoryManagement.consumptionList.inputConsumption.InputConsumptionPresenter;
import com.binar.core.inventoryManagement.consumptionList.inputConsumption.InputConsumptionViewImpl;
import com.binar.core.inventoryManagement.receptionList.ReceptionListView.ReceptionListListener;
import com.binar.core.inventoryManagement.receptionList.inputReception.InputReceptionModel;
import com.binar.core.inventoryManagement.receptionList.inputReception.InputReceptionPresenter;
import com.binar.core.inventoryManagement.receptionList.inputReception.InputReceptionViewImpl;
import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.binar.entity.GoodsReception;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.LoginManager;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class ReceptionListPresenter implements ReceptionListListener {
	
	GeneralFunction function;
	ReceptionListModel model;
	ReceptionListViewImpl view;
	InputReceptionModel formRecModel;
	InputReceptionViewImpl formRecView;
	InputReceptionPresenter formRecPresenter;
	String currentIdGoods;
	int currentQuantity;
	DateTime currentDate;
	
	LoginManager loginManager;
	

	/*
	 *  Manajemen ROLE level Fungsionalitas
	 *   
	 */
	boolean withEditReceipt= false;
	public void roleProcessor(){
		String role=loginManager.getRoleId();
		if(!role.equals(loginManager.FRM)){
			view.hideButtonNew();
			withEditReceipt=false;
		}else{
			view.showButtonNew();
			withEditReceipt=true;
		}
		
	}

	
	public ReceptionListPresenter(GeneralFunction function, 
			ReceptionListModel model, ReceptionListViewImpl view){
		this.model=model;
		this.view=view;
		this.function=function;
		this.loginManager=function.getLogin();

		view.setListener(this);
		
		view.init();
		roleProcessor();

		updateTable(view.getSelectedPeriod()); 
		updateTableByDate(view.getSelectedPeriod());
	}
	
	@Override
	public void updateTable(String input) {
		System.out.println("input "+input);
		System.out.println("Bulan ini" + Calendar.getInstance().get(Calendar.MONTH));
		DateTime date=function.getDate().parseDateMonth(input);
		Map<Goods, Integer> dataTable=model.getTableData(date);
		Map<DateTime, Integer> dataTable2=model.getTableDataByDate(date);
		view.updateTableData(dataTable);
		if(currentIdGoods!=null){
			List<GoodsReception> reception=model.getReceptions(currentIdGoods, 
					function.getDate().parseDateMonth(view.getSelectedPeriod()));
			int quantity=model.getTableDataQuantity(function.getDate().
					parseDateMonth(view.getSelectedPeriod()), currentIdGoods);
			view.setLabelData(reception, quantity, withEditReceipt);
		}
	}

	@Override
	public void updateTableByDate(String input) {
		System.out.println("Update table by date -===============input "+input);
		System.out.println("Bulan ini" + Calendar.getInstance().get(Calendar.MONTH));
		DateTime date=function.getDate().parseDateMonth(input);
		Map<DateTime, Integer> dataTableByDate=model.getTableDataByDate(date);
		view.updateTableDataByDate(dataTableByDate);
		try {
			List<GoodsReception> data=model.getReceptionsByDate(this.currentDate);
			view.setLabelDataByDate(data, withEditReceipt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void showDetail(String idGoods, int quantity) {
		List<GoodsReception> reception=model.getReceptions(idGoods, 
				function.getDate().parseDateMonth(view.getSelectedPeriod()));
		this.currentIdGoods=idGoods;
		view.showDetailWindow(reception, quantity, withEditReceipt);
	}

	@Override
	public void delete(int recId) {
		final int recIdFinal=recId;
		function.showDialog("Yakin Hapus Data?",
				"Yakin akan menghapus data penerimaan?", 
				new ClickListener() {
					
					public void buttonClick(ClickEvent event) {
						boolean success=model.deleteGoodsReception(recIdFinal);
						if(success){
							Notification.show("Data telah dihapus");
						}else{
							Notification.show("Data gagal dihapus", Type.ERROR_MESSAGE);
						}
						//update tampilan tabel
						updateTable(view.getSelectedPeriod());
						updateTableByDate(view.getSelectedPeriod());
						List<GoodsReception> data=model.getReceptionsByDate(view.getCurrentDate());
						view.setLabelDataByDate(data, withEditReceipt);						
					}
				}, 
				view.getUI());
	}

	@Override
	public void edit(int recId) {
		if(formRecModel==null){
			formRecModel=new InputReceptionModel(function);
			formRecView=new InputReceptionViewImpl(function);
			formRecPresenter =new InputReceptionPresenter(formRecModel, formRecView,
					function, recId, true );
			System.out.println("Form model view presenter instantiasi");
		}else{
			
			formRecPresenter.updateEditView(recId);
		}
		Window windowEdit=view.displayFormEdit(formRecView, "Ubah Data Penerimaan");	
		formRecPresenter.setWindow(windowEdit);
		//menambahkan listener, agar ketika window diclose, tampilan table akan diupdate
		Collection<Window> windows=view.getUI().getWindows();
		for(Window window:windows){
			window.addCloseListener(new CloseListener() {
				public void windowClose(CloseEvent e) {
					updateTable(view.getSelectedPeriod());
					updateTableByDate(view.getSelectedPeriod());
				}
			});
		}
	}

	@Override
	public void buttonClick(String source, Object data) {
		if(source.equals("buttonInput")){
			if(formRecModel==null){
				formRecModel=new InputReceptionModel(function);
				formRecView=new InputReceptionViewImpl(function);
				formRecPresenter =new InputReceptionPresenter(formRecModel, formRecView,
						function);
				System.out.println("Form model view presenter instantiasi"+view.window.getCaption());
				formRecPresenter.setEditMode(false);
			}else{
				System.out.println("Data = "+data.toString());
				formRecPresenter.setEditMode(false);
			}
			//form yang ditampilkan, dan judul jendelanya
			view.displayForm(formRecView,"Masukan Pengeluaran Harian");			
			//menambahkan listener, agar ketika window diclose, tampilan table akan diupdate
			Collection<Window> windows=view.getUI().getWindows();
			for(Window window:windows){
				window.addCloseListener(new CloseListener() {
					public void windowClose(CloseEvent e) {
						updateTable(view.getSelectedPeriod());
						updateTableByDate(view.getSelectedPeriod());
					}
				});
			}			
		}
	}
	@Override
	public void showDetailByDate(DateTime date) {
		List<GoodsReception> data=model.getReceptionsByDate(date);
		System.out.println("Good Reception Size"+data.size());
		view.showDetailWindowByDate(data, date, withEditReceipt);
		this.currentDate = date;
	}

	@Override
	public int getNumberOfReceptionsByDate(DateTime periode) {
		return model.getNumberOfReceptionsByDate(periode);
	}

	@Override
	public void getViewMode(String selectedViewMode) {
		if (selectedViewMode=="barang"){
			view.setViewMode("barang");
			updateTable(view.getSelectedPeriod());
		}
		if (selectedViewMode=="tanggal_penerimaan"){
			view.setViewMode("tanggal_penerimaan");
			updateTableByDate(view.getSelectedPeriod());
		}
		else{
			System.err.println("View mode = "+selectedViewMode);				
		}
	}

	@Override
	public List<GoodsReception> getRecByDate(DateTime date) {
		return model.getReceptionsByDate(date);
	}

}