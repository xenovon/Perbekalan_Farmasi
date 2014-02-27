package com.binar.generalFunction;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.enumeration.EnumStockStatus;

public class MinimumStockUpdater {

	/*
	 * kelas untuk mengubah status stok minimal
	 * di instantiasi di
	 */
	//berapa angka selisih antara stok saat ini dengan angka minimum stock hingga dikasih peringatan
	public final int MINIMUM_STOCK_CRITERIA=20; //dalam persen
	EbeanServer server;
	public MinimumStockUpdater(EbeanServer server) {
		this.server=server;
	}
	public void update(String idGoods){
		try {
			Goods goods=server.find(Goods.class, idGoods);
			int currentStock=goods.getCurrentStock();
			int minimalStock=goods.getMinimumStock();
			int minimalStockSafe=minimalStock+ (minimalStock * MINIMUM_STOCK_CRITERIA / 100);
			if(currentStock>minimalStockSafe){
				goods.setStockStatus(EnumStockStatus.SAFE);
			}else if(currentStock<=minimalStock){
				goods.setStockStatus(EnumStockStatus.LESS);
			}else{
				goods.setStockStatus(EnumStockStatus.WARNING);
			}
			server.update(goods);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	
}
