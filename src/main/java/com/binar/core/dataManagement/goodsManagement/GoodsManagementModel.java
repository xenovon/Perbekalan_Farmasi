package com.binar.core.dataManagement.goodsManagement;

import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.generalFunction.GeneralFunction;

public class GoodsManagementModel {

	GeneralFunction function;
	EbeanServer server;
	public GoodsManagementModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
	}
	public List<Goods> getGoods(){
		return server.find(Goods.class).findList();
	}
	
	public Goods getSingleGoods(String idGoods){
		return server.find(Goods.class,idGoods);
	}	
}
