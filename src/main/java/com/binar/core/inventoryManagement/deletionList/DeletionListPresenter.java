package com.binar.core.inventoryManagement.deletionList;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.binar.core.inventoryManagement.consumptionList.inputConsumption.InputConsumptionModel;
import com.binar.core.inventoryManagement.consumptionList.inputConsumption.InputConsumptionPresenter;
import com.binar.core.inventoryManagement.consumptionList.inputConsumption.InputConsumptionViewImpl;
import com.binar.core.inventoryManagement.deletionList.DeletionListView.ApprovalFilter;
import com.binar.core.inventoryManagement.deletionList.DeletionListView.DeletionListListener;
import com.binar.core.inventoryManagement.deletionList.inputDeletion.InputDeletionModel;
import com.binar.core.inventoryManagement.deletionList.inputDeletion.InputDeletionPresenter;
import com.binar.core.inventoryManagement.deletionList.inputDeletion.InputDeletionViewImpl;
import com.binar.entity.DeletedGoods;
import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.LoginManager;
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
	InputDeletionModel formDelEditModel;
	InputDeletionViewImpl formDelEditView;
	InputDeletionPresenter formDelEditPresenter;
	LoginManager loginManager;

	String currentIdGoods;
	int currentQuantity;
	DateTime currentDate;
	
	public DeletionListPresenter(GeneralFunction function, 
			DeletionListModel model, DeletionListViewImpl view){
		this.model=model;
		this.view=view;
		this.function=function;
		this.loginManager=function.getLogin();

		view.init();
		view.setListener(this);
		roleProcessor();

		updateTable();
	}
	/*
	 *  Manajemen ROLE level Fungsionalitas
	 *   
	 */
	boolean withEditDeletion = false;
	public void roleProcessor(){
		String role=loginManager.getRoleId();
		if(!role.equals(loginManager.FRM)){
			view.hideButtonNew();
			withEditDeletion=false;
		}else{
			view.showButtonNew();
			withEditDeletion=true;
		}
		
	}
	public void updateTable() {
		Date rangeStart=view.getSelectedEndRange();
		Date rangeEnd=view.getSelectedStartRange();
		
		ApprovalFilter approval=view.getApprovalFilter();
		List<DeletedGoods> data=model.getDeletedTable(rangeStart, rangeEnd, approval);
		System.out.println("ukuran deleted goods "+data.size());
		view.updateTableData(data, withEditDeletion);
	}


	@Override
	public void newDeletion() {
		if(formDelModel==null){
			formDelModel=new InputDeletionModel(function);
			formDelView=new InputDeletionViewImpl(function);
			formDelPresenter =new InputDeletionPresenter(formDelModel, formDelView,
					function);
		}
		//form yang ditampilkan, dan judul jendelanya
		view.displayForm(formDelView,"Masukan Barang yang akan dihapus");			
		//menambahkan listener, agar ketika window diclose, tampilan table akan diupdate
		Collection<Window> windows=view.getUI().getWindows();
		for(Window window:windows){
			window.addCloseListener(new CloseListener() {
				public void windowClose(CloseEvent e) {
					updateTable();
				}
			});
		}			
		
	}

	@Override
	public void editClick(int idDeletion) {
		if(formDelEditModel==null){
			formDelEditModel=new InputDeletionModel(function);
			formDelEditView=new InputDeletionViewImpl(function);
			formDelEditPresenter =new InputDeletionPresenter(formDelEditModel, formDelEditView, function, idDeletion, true);
			System.out.println("Form model view presenter instantiasi");
		}else{
			formDelEditPresenter.updateEditView(idDeletion);	
		}
		view.displayForm(formDelEditView, "Ubah Data Pengajuan Penghapusan");
		
		//menambahkan listener, agar ketika window diclose, tampilan table akan diupdate
		Collection<Window> windows=view.getUI().getWindows();
		for(Window window:windows){
			window.addCloseListener(new CloseListener() {
				public void windowClose(CloseEvent e) {
					updateTable();
				}
			});
		}
		
	}

	@Override
	public void deleteClick(final int idDeletion) {
		function.showDialog("Yakin Hapus Data?",
				"Yakin akan menghapus data pengajuan penghapusan obat?", 
				new ClickListener() {
					
					public void buttonClick(ClickEvent event) {
						String error=model.deleteDeletionGoods(idDeletion);
						if(error==null){
							Notification.show("Data telah dihapus");
						}else{
							Notification.show("Data gagal dihapus", Type.ERROR_MESSAGE);
						}
						//update tampilan tabel
						updateTable();
					}
				}, 
				view.getUI());
		
	}

	@Override
	public void showClick(int idDeletion) {
		DeletedGoods deletedGoods=model.getSingleDeletedGoods(idDeletion);
		view.showDetailWindow(deletedGoods);
		
	}

	@Override
	public void dateRangeChange() {
		updateTable();
	}	
}

