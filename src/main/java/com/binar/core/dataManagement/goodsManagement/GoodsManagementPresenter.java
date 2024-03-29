package com.binar.core.dataManagement.goodsManagement;

import java.util.Collection;
import java.util.List;

import com.binar.core.dataManagement.goodsManagement.GoodsManagementView.GoodsManagementListener;
import com.binar.core.dataManagement.goodsManagement.inputEditGoods.InputGoodsModel;
import com.binar.core.dataManagement.goodsManagement.inputEditGoods.InputGoodsPresenter;
import com.binar.core.dataManagement.goodsManagement.inputEditGoods.InputGoodsViewImpl;
import com.binar.entity.Goods;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.LoginManager;
import com.google.gwt.dom.client.ModElement;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.WindowCloseListener;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class GoodsManagementPresenter implements GoodsManagementListener {

	GoodsManagementModel model;
	GoodsManagementViewImpl view;
	GeneralFunction function;
	
	InputGoodsViewImpl viewInput;
	InputGoodsModel modelInput;
	InputGoodsPresenter presenterInput;
	
	InputGoodsViewImpl viewEdit;
	InputGoodsPresenter presenterEdit;
	
	LoginManager loginManager;

	public GoodsManagementPresenter(GoodsManagementModel model, 
									GoodsManagementViewImpl view, GeneralFunction function) {
		this.model=model;
		this.view=view;
		this.function=function;
		view.init();
		this.loginManager=function.getLogin();

		view.setListener(this);
		roleProcessor();
		updateTable();
	
	}
	/*
	 *  Manajemen ROLE level Fungsionalitas
	 *   
	 */
	boolean withEditGoods = false;
	public void roleProcessor(){
		String role=loginManager.getRoleId();
		if(!role.equals(loginManager.FRM)){
			view.hideButtonNew();
			withEditGoods=false;
		}else{
			view.showButtonNew();
			withEditGoods=true;
		}
		
	}

	@Override
	public void buttonClick(String buttonName) {
		if(buttonName.equals("buttonInput")){
			showInputWindow();
		}
	}
	private void showInputWindow(){
		if(presenterInput==null){
			modelInput =new InputGoodsModel(function);
			viewInput = new InputGoodsViewImpl(function);
			presenterInput=new InputGoodsPresenter(modelInput, viewInput, function, false);
		}
		viewInput.resetForm();		
		view.displayForm(viewInput, "Masukan Data Barang Baru");
		addWIndowCloseListener();
	}
	@Override
	public void editClick(String idGoods) {
		if(presenterEdit==null){
			modelInput =new InputGoodsModel(function);
			viewEdit = new InputGoodsViewImpl(function);
			presenterEdit=new InputGoodsPresenter(modelInput, viewEdit, function, true);
		}
		viewEdit.resetForm();	
		presenterEdit.setFormData(idGoods);
		view.displayForm(viewEdit, "Ubah Data Barang");
		addWIndowCloseListener();
	}

	@Override
	public void deleteClick(String idGoods) {
		final String finalIdGoods=idGoods;
		Goods goods=model.getSingleGoods(idGoods);
		function.showDialog("Hapus Data", "Yakin akan menghapus data barang "+goods.getName(),
				new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						String result=model.deleteGoods(finalIdGoods);
						if(result!=null){
							Notification.show(result, Type.ERROR_MESSAGE);
						}else{
							updateTable();
							Notification.show("Data sukses dihapus");
						}
					}
				}, view.getUI());
	}

	@Override
	public void showClick(String idGoods) {
		view.showDetailWindow(model.getSingleGoods(idGoods));
	}
	public void updateTable(){
		List<Goods> goods=model.getGoods();
		view.updateTableData(goods, withEditGoods);
	}
	public void addWIndowCloseListener(){
		Collection<Window> windows=view.getUI().getWindows();
		for(Window window:windows){
			window.addCloseListener(new CloseListener() {
				public void windowClose(CloseEvent e) {
					updateTable();
				}
			});
		}

	}


}
