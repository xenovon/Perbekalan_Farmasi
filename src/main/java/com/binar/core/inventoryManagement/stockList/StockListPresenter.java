package com.binar.core.inventoryManagement.stockList;

import java.util.Date;
import java.util.List;

import com.binar.core.inventoryManagement.stockList.StockListView.StockListListener;
import com.binar.core.inventoryManagement.stockList.StockListView.TableData;
import com.binar.entity.GoodsConsumption;
import com.binar.entity.GoodsReception;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class StockListPresenter implements StockListListener{

	GeneralFunction function;
	StockListModel model;
	StockListViewImpl view;
	
	public StockListPresenter(StockListModel model, StockListViewImpl view, GeneralFunction function) {
		this.view=view;
		this.model=model;
		this.function=function;
		this.view.setListener(this);
		view.init();
		view.setComboGoodsData(model.getGoodsData());
	}
	@Override
	public void showConsumptionDetail(int idConsumption) {
		GoodsConsumption consumption=model.getConsumption(idConsumption);
		view.showDetailConsumption(consumption);
		
	}

	@Override
	public void showReceptionDetail(int idReception) {
		GoodsReception reception=model.getReception(idReception);
		view.showDetailReception(reception);
		
	}

	@Override
	public void goodsChange() {
		Date startDate = view.getSelectStartDate();
		Date endDate =view.getSelectEndDate();
		String idGoods=view.getSelectedGoods();
		view.updateTableData(model.getTableData(idGoods, startDate, endDate));
	}

	@Override
	public void periodChange() {
		Date startDate = view.getSelectStartDate();
		Date endDate =view.getSelectEndDate();
		String idGoods=view.getSelectedGoods();
		List<TableData> data=model.getTableData(idGoods, startDate, endDate);
		if(data.size()>0){
			view.updateTableData(data);					
		}else{
			Notification.show("Data kosong",Type.TRAY_NOTIFICATION);
		}
	}




}
