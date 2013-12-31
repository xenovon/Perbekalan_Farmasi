package com.binar.core.inventoryManagement.deletionList;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.binar.core.inventoryManagement.consumptionList.inputConsumption.InputConsumptionModel;
import com.binar.core.inventoryManagement.consumptionList.inputConsumption.InputConsumptionPresenter;
import com.binar.core.inventoryManagement.consumptionList.inputConsumption.InputConsumptionViewImpl;
import com.binar.core.inventoryManagement.deletionList.DeletionListView.DeletionListListener;
import com.binar.core.inventoryManagement.deletionList.inputDeletion.InputDeletionModel;
import com.binar.core.inventoryManagement.deletionList.inputDeletion.InputDeletionPresenter;
import com.binar.core.inventoryManagement.deletionList.inputDeletion.InputDeletionViewImpl;
import com.binar.entity.DeletedGoods;
import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class DeletionListPresenter implements DeletionListListener{
	
	GeneralFunction function;
	DeletionListModel model;
	DeletionListViewImpl view;
	InputDeletionModel formDelModel;
	InputDeletionViewImpl formDelView;
	InputDeletionPresenter formDelPresenter;
	String currentIdGoods;
	int currentQuantity;
	DateTime currentDate;
	
	public DeletionListPresenter(GeneralFunction function, 
			DeletionListModel model, DeletionListViewImpl view){
		this.model=model;
		this.view=view;
		this.function=function;
		view.init();
		view.setListener(this);
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
			List<DeletedGoods> deletion=model.getDeletions(currentIdGoods, 
					function.getDate().parseDateMonth(view.getSelectedPeriod()));
			int quantity=model.getTableDataQuantity(function.getDate().
					parseDateMonth(view.getSelectedPeriod()), currentIdGoods);
			view.setLabelData(deletion, quantity);
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
			List<DeletedGoods> data=model.getDeletionsByDate(this.currentDate);
			view.setLabelDataByDate(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void showDetail(String idGoods, int quantity) {
		List<DeletedGoods> deletion=model.getDeletions(idGoods, 
				function.getDate().parseDateMonth(view.getSelectedPeriod()));
		this.currentIdGoods=idGoods;
		System.out.println(deletion.size()+" ukuran");
		view.showDetailWindow(deletion, quantity);
	}

	@Override
	public void delete(int delGoodsId) {
		final int delGoodsIdFinal=delGoodsId;
		function.showDialog("Yakin Hapus Data?",
				"Yakin akan menghapus data pengajuan penghapusan obat?", 
				new ClickListener() {
					
					public void buttonClick(ClickEvent event) {
						boolean success=model.deleteDeletedGoods(delGoodsIdFinal);
						if(success){
							Notification.show("Data telah dihapus");
						}else{
							Notification.show("Data gagal dihapus", Type.ERROR_MESSAGE);
						}
						//update tampilan tabel
						updateTable(view.getSelectedPeriod());
						updateTableByDate(view.getSelectedPeriod());
						List<DeletedGoods> data=model.getDeletionsByDate(view.getCurrentDate());
						view.setLabelDataByDate(data);						
					}
				}, 
				view.getUI());
	}

	@Override
	public void edit(int delGoodsId) {
		if(formDelModel==null){
			formDelModel=new InputDeletionModel(function);
			formDelView=new InputDeletionViewImpl(function);
			formDelPresenter =new InputDeletionPresenter(formDelModel, formDelView,
					function, view.getSelectedPeriod(),view.getWindowEdit(), delGoodsId, true);
			System.out.println("Form model view presenter instantiasi");
		}else{
			formDelPresenter.updateEditView(delGoodsId, true, view.getWindowEdit());	
		}
		view.displayFormEdit(formDelView, "Ubah Data Pengajuan Penghapusan");
		
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
			if(formDelModel==null){
				formDelModel=new InputDeletionModel(function);
				formDelView=new InputDeletionViewImpl(function);
				formDelPresenter =new InputDeletionPresenter(formDelModel, formDelView,
						function, (String)data,view.getWindowEdit());
				System.out.println("Form model view presenter instantiasi"+view.window.getCaption());
				formDelPresenter.setEditMode(false, view.getWindow());
			}else{
				System.out.println("Data = "+data.toString());
				formDelPresenter.setPeriode((String)data,view.getWindow());
				formDelPresenter.setEditMode(false, view.getWindow());
			}
			//form yang ditampilkan, dan judul jendelanya
			view.displayForm(formDelView,"Masukan Barang yang akan dihapus");			
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
		List<DeletedGoods> data=model.getDeletionsByDate(date);
		view.showDetailWindowByDate(data, date);
		this.currentDate = date;
	}

	@Override
	public List<DeletedGoods> getDelGoodsByDate(DateTime date) {
		return model.getDeletionsByDate(date);
	}

	@Override
	public int getNumberOfDeletionsByDate(DateTime periode) {
		return model.getNumberOfDeletionsByDate(periode);
	}

	@Override
	public void getViewMode(String selectedViewMode) {
		if (selectedViewMode=="barang"){
			view.setViewMode("barang");
			updateTable(view.getSelectedPeriod());
		}
		if (selectedViewMode=="tanggal_pengajuan"){
			view.setViewMode("tanggal_pengajuan");
			updateTableByDate(view.getSelectedPeriod());
		}
		else{
			System.err.println("View mode = "+selectedViewMode);				
		}
	}	
}

