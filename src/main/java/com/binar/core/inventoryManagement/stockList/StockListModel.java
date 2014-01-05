package com.binar.core.inventoryManagement.stockList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.core.inventoryManagement.stockList.StockListView.TableData;
import com.binar.entity.Goods;
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
	public Map<String, String> getGoodsData(){
		List<Goods> goodsList=server.find(Goods.class).findList();
		Map<String, String> data=new TreeMap<String, String>();
		for(Goods goods:goodsList){
			//get akses
			if(goods.getInsurance().isShowInDropdown()){
				data.put(goods.getIdGoods(), goods.getName() + " - "+goods.getInsurance().getName());				
			}else{
				data.put(goods.getIdGoods(), goods.getName());				
			}
		}
		return data;
	}
	public List<TableData> getTableData(String idGoods, Date start, Date end){
		Goods goods=getGoods(idGoods);
		List<TableData> output=new ArrayList<TableData>();
		DateTime startDate=new DateTime(start);
		DateTime endDate=new DateTime(end);
		if(startDate.compareTo(endDate)>0){
			DateTime buffer=startDate;
			startDate=endDate;
			endDate=buffer;
		}
		try {
			DateTime currentDate=startDate;
			//melakukan perulangan tiap harinya....
			while(currentDate.compareTo(endDate) < 0){
				DateTime todayStart=currentDate.withHourOfDay(currentDate.hourOfDay().getMinimumValue());
				DateTime todayEnd=currentDate.withHourOfDay(currentDate.hourOfDay().getMaximumValue());
				System.out.println(currentDate.toString());
				List<GoodsReception> receptions=server.find(GoodsReception.class).where().eq("invoiceItem.purchaseOrderItem.supplierGoods.goods", 
						goods).between("date", todayStart, todayEnd).findList();
				List<GoodsConsumption> consumptions=server.find(GoodsConsumption.class).where().eq("goods", goods).
						where().between("consumptionDate", todayStart, todayEnd).findList();
			
				int iterateCount;
				if(receptions.size()>=consumptions.size()){
					iterateCount=receptions.size();
				}else{
					iterateCount=consumptions.size();
				}
				//jika consumption dan reception tidak kosong;
				if(iterateCount!=0){
					for(int i=0;i<iterateCount;i++){
						TableData data=new TableData();					
						data.setDate(todayStart);
						try {
							GoodsConsumption consumption=consumptions.get(i);
							data.setConsumption(consumption);
						} catch (Exception e) {
							
						}
						try {
							GoodsReception reception=receptions.get(i);
							data.setReception(reception);
						} catch (Exception e) {
						}
						output.add(data);
					}				
				}
				
				currentDate=currentDate.plusDays(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}
	
	public GoodsConsumption getConsumption(int idConsumption){
		return server.find(GoodsConsumption.class, idConsumption);
	}
	public GoodsReception getReception(int idReception){
		return server.find(GoodsReception.class, idReception);
	}
	private Goods getGoods(String idGoods){
		return server.find(Goods.class, idGoods);
	}
	
	public static void main(String[] args) {
		DateTime startDate=new DateTime().plusDays(10);
		DateTime endDate=new DateTime().plusDays(5);
		if(startDate.compareTo(endDate)>0){
			DateTime buffer=startDate;
			startDate=endDate;
			endDate=buffer;
		}
		DateTime currentDate=startDate;
		while(currentDate.compareTo(endDate) < 0){
			System.out.println(currentDate.toString());
			
			currentDate=currentDate.plusDays(1);
		}

	}
}
