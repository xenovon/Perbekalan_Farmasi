package com.binar.core.inventoryManagement.stockList;

import java.util.List;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.core.inventoryManagement.stockList.StockListView.TableData;
import com.binar.entity.GoodsConsumption;
import com.binar.entity.GoodsReception;
import com.binar.generalFunction.GeneralFunction;

public class StockListModel {

	GeneralFunction function;
	EbeanServer server;
	
	public StockListModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
	}
	public List<TableData> getTableData(String idGoods, DateTime month, DateTime year){
		
		return null;
	}
	
	public GoodsConsumption getConsumption(int idConsumption){
		return server.find(GoodsConsumption.class, idConsumption);
	}
	public GoodsReception getReception(int idReception){
		return server.find(GoodsReception.class, idReception);
	}
	
}
