package com.binar.core.dashboard.dashboardItem.farmationExpireGoods;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.GoodsReception;
import com.binar.entity.enumeration.EnumStockStatus;
import com.binar.generalFunction.GeneralFunction;

public class FarmationExpiredGoodsModel {

	GeneralFunction function;
	EbeanServer server;
	public FarmationExpiredGoodsModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
	}
	public List<GoodsReception> getFarmationExpiredGoods(){
		//
		List<GoodsReception> receptionList=new ArrayList<GoodsReception>();
		//
		List<GoodsReception> goodsExpired=server.find(GoodsReception.class).where().gt("expired_date", DateTime.now().minusMonths(2)).
				orderBy().asc("expired_date").setMaxRows(150).findList();
		for(GoodsReception reception:goodsExpired){
			if(isCloseToExpiry(reception)){
				receptionList.add(reception);
			}
		}
		return receptionList;
		
	}
	private boolean isCloseToExpiry(GoodsReception reception){
		//dianggap dekat dengan expired date jika kurang dari 3 bulan dan setelah kadaluarsa berlalu selama 4 bulan
		DateTime expiredLimit=DateTime.now().plusMonths(3);
		DateTime expiredDate=new DateTime(reception.getExpiredDate());
		
		//Jika expired date ada diantara 3 bulan kedepan dan 4 bulan kebelakang
		if(expiredDate.isBefore(expiredLimit)){
			if(expiredDate.isAfter(DateTime.now().minusMonths(4))){ 
				return true;				
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
}
