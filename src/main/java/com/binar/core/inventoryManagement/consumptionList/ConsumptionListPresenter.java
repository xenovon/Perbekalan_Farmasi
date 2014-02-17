package com.binar.core.inventoryManagement.consumptionList;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.binar.core.inventoryManagement.consumptionList.ConsumptionListView.ConsumptionListListener;
import com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm.InputFormModel;
import com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm.InputFormPresenter;
import com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm.InputFormViewImpl;
import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.binar.generalFunction.LoginManager;
import com.binar.core.inventoryManagement.consumptionList.inputConsumption.InputConsumptionModel;
import com.binar.core.inventoryManagement.consumptionList.inputConsumption.InputConsumptionPresenter;
import com.binar.core.inventoryManagement.consumptionList.inputConsumption.InputConsumptionViewImpl;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class ConsumptionListPresenter implements ConsumptionListListener{
	
	GeneralFunction function;
	ConsumptionListModel model;
	ConsumptionListViewImpl view;
	InputConsumptionModel formConsModel;
	InputConsumptionViewImpl formConsView;
	InputConsumptionPresenter formConsPresenter;
	String currentIdGoods;
	int currentQuantity;
	DateTime currentDate;
	LoginManager loginManager;

	
	public ConsumptionListPresenter(GeneralFunction function, 
			ConsumptionListModel model, ConsumptionListViewImpl view){
		this.model=model;
		this.view=view;
		this.function=function;
		this.loginManager=function.getLogin();

		view.init();
		view.setListener(this);
		roleProcessor();

		updateTable(view.getSelectedPeriod(), true); 
		updateTableByDate(view.getSelectedPeriod(), true);
	}

	/*
	 *  Manajemen ROLE level Fungsionalitas
	 *   
	 */
	boolean withEditConsumption= false;
	public void roleProcessor(){
		String role=loginManager.getRoleId();
		if(!role.equals(loginManager.FRM)){
			view.hideButtonNew();
			withEditConsumption=false;
		}else{
			view.showButtonNew();
			withEditConsumption=true;
		}
		
	}

	@Override
	public void updateTable(String input, boolean isConstructor) {
		System.out.println("input "+input);
		System.out.println("Bulan ini" + Calendar.getInstance().get(Calendar.MONTH));
		DateTime date=function.getDate().parseDateMonth(input);
		Map<Goods, Integer> dataTable=model.getTableData(date);
		Map<DateTime, Integer> dataTable2=model.getTableDataByDate(date);
		if(dataTable2.size()==0){
			if(!isConstructor){
				Notification.show("Data pengeluaran kosong", Type.TRAY_NOTIFICATION);				
			}
		}
		view.updateTableData(dataTable);
		if(currentIdGoods!=null){
			List<GoodsConsumption> consumption=model.getConsumptions(currentIdGoods, 
					function.getDate().parseDateMonth(view.getSelectedPeriod()));
			int quantity=model.getTableDataQuantity(function.getDate().
					parseDateMonth(view.getSelectedPeriod()), currentIdGoods);
			view.setLabelData(consumption, quantity, withEditConsumption);
		}
	}

	@Override
	public void updateTableByDate(String input, boolean isConstructor) {
		System.out.println("Update table by date -===============input "+input);
		System.out.println("Bulan ini" + Calendar.getInstance().get(Calendar.MONTH));
		DateTime date=function.getDate().parseDateMonth(input);
		Map<DateTime, Integer> dataTableByDate=model.getTableDataByDate(date);
		if(dataTableByDate.size()==0){
			if(!isConstructor){
				Notification.show("Data pengeluaran kosong", Type.TRAY_NOTIFICATION);				
			}
		}
		view.updateTableDataByDate(dataTableByDate);
		try {
			List<GoodsConsumption> data=model.getConsumptionsByDate(this.currentDate);
			view.setLabelDataByDate(data, withEditConsumption);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void showDetail(String idGoods, int quantity) {
		List<GoodsConsumption> consumption=model.getConsumptions(idGoods, 
				function.getDate().parseDateMonth(view.getSelectedPeriod()));
		this.currentIdGoods=idGoods;
		view.showDetailWindow(consumption, quantity, withEditConsumption);
	}
	@Override
	public void showDetailByDate(DateTime date) {
		List<GoodsConsumption> data=model.getConsumptionsByDate(date);
		view.showDetailWindowByDate(data, date, withEditConsumption);
		this.currentDate = date;
	}

	@Override
	public void delete(int consId) {
		final int consIdFinal=consId;
		function.showDialog("Yakin Hapus Data?",
				"Yakin akan menghapus data pengeluaran harian?", 
				new ClickListener() {
					
					public void buttonClick(ClickEvent event) {
						boolean success=model.deleteGoodsConsumption(consIdFinal);
						if(success){
							Notification.show("Data telah dihapus");
						}else{
							Notification.show("Data gagal dihapus", Type.ERROR_MESSAGE);
						}
						//update tampilan tabel
						updateTable(view.getSelectedPeriod(), false);
						updateTableByDate(view.getSelectedPeriod(), false);
						List<GoodsConsumption> data=model.getConsumptionsByDate(view.getCurrentDate());
						view.setLabelDataByDate(data, withEditConsumption);						
					}
				}, 
				view.getUI());
	}

	@Override
	public void edit(int consId) {
		if(formConsModel==null){
			formConsModel=new InputConsumptionModel(function);
			formConsView=new InputConsumptionViewImpl(function);
			formConsPresenter =new InputConsumptionPresenter(formConsModel, formConsView,
					function, view.getSelectedPeriod(),view.getWindowEdit(), consId, true);
			System.out.println("Form model view presenter instantiasi");
		}else{
			formConsPresenter.updateEditView(consId, true, view.getWindowEdit());	
		}
		view.displayFormEdit(formConsView, "Ubah Data Pengeluaran Harian");
		
		//menambahkan listener, agar ketika window diclose, tampilan table akan diupdate
		Collection<Window> windows=view.getUI().getWindows();
		for(Window window:windows){
			window.addCloseListener(new CloseListener() {
				public void windowClose(CloseEvent e) {
					updateTable(view.getSelectedPeriod(), false);
					updateTableByDate(view.getSelectedPeriod(), false);
				}
			});
		}
	}

	@Override
	public void buttonClick(String source, Object data) {
		if(source.equals("buttonInput")){
			if(formConsModel==null){
				formConsModel=new InputConsumptionModel(function);
				formConsView=new InputConsumptionViewImpl(function);
				formConsPresenter =new InputConsumptionPresenter(formConsModel, formConsView,
						function, (String)data,view.getWindowEdit());
				System.out.println("Form model view presenter instantiasi"+view.window.getCaption());
				formConsPresenter.setEditMode(false, view.getWindow());
			}else{
				System.out.println("Data = "+data.toString());
				formConsPresenter.setPeriode((String)data,view.getWindow());
				formConsPresenter.setEditMode(false, view.getWindow());
			}
			//form yang ditampilkan, dan judul jendelanya
			view.displayForm(formConsView,"Masukan Pengeluaran Harian");			
			//menambahkan listener, agar ketika window diclose, tampilan table akan diupdate
			Collection<Window> windows=view.getUI().getWindows();
			for(Window window:windows){
				window.addCloseListener(new CloseListener() {
					public void windowClose(CloseEvent e) {
						updateTable(view.getSelectedPeriod(), false);
						updateTableByDate(view.getSelectedPeriod(), false);
					}
				});
			}			
		}
	}

	@Override
	public List<GoodsConsumption> getConsByDate(DateTime date) {
		return model.getConsumptionsByDate(date);
	}

	@Override
	public int getNumberOfConsumptionsByDate(DateTime periode) {
		return model.getNumberOfConsumptionsByDate(periode);
	}

	@Override
	public void getViewMode(String selectedViewMode) {
		if (selectedViewMode=="barang"){
			view.setViewMode("barang");
			updateTable(view.getSelectedPeriod(), false);
		}
		if (selectedViewMode=="tanggal_pengeluaran"){
			view.setViewMode("tanggal_pengeluaran");
			updateTableByDate(view.getSelectedPeriod(), false);
		}
		else{
			System.err.println("View mode = "+selectedViewMode);				
		}
	}	
}