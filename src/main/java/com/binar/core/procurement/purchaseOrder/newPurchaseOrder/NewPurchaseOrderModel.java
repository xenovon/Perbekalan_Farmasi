package com.binar.core.procurement.purchaseOrder.newPurchaseOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.core.procurement.purchaseOrder.newPurchaseOrder.NewPurchaseOrderView.FormData;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.PurchaseOrderItem;
import com.binar.entity.ReqPlanning;
import com.binar.entity.Role;
import com.binar.entity.User;
import com.binar.entity.enumeration.EnumGoodsType;
import com.binar.entity.enumeration.EnumPurchaseOrderType;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;

public class NewPurchaseOrderModel {
	
	GeneralFunction function;
	EbeanServer server;
	GetSetting setting;
	
	public NewPurchaseOrderModel(GeneralFunction function) {
		this.function=function;
		server=function.getServer();
		setting=function.getSetting();
	}
	//mendapatkan id dan String user dengan role ifrs; 
	public Map<Integer, String> getUserComboData(){
		Role role=server.find(Role.class, "IFRS");
		List<User> users=server.find(User.class).where().eq("role", role).findList();
		
		Map<Integer, String> returnValue=new HashMap<Integer, String>(users.size());
		for(User user:users){
			returnValue.put(user.getIdUser(), user.getName() +" - "+user.getRole().getRoleName() );
		}
		
		return returnValue;
		
	}
	public List<ReqPlanning> getReqPlanning(DateTime periode){
		try {
			Date startDate=periode.toDate();
			Date endDate=periode.withDayOfMonth(periode.dayOfMonth().getMaximumValue()).toDate();
			List<ReqPlanning> returnValue=server.find(ReqPlanning.class).where().
					between("period", startDate, endDate).eq("isAccepted", true).findList();
			return returnValue;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	//format nomor pesanan ex : PSI/10/2013/3   
	private String generatePurchaseOrderNumber(PurchaseOrder order, int currentNumber){
		// currentNumber : urutan saat ini;
		String prefiks;
		String month=String.valueOf(DateTime.now().getMonthOfYear());
		String year=String.valueOf(DateTime.now().getYear());
		int purchaseNumber=0;
	
		if(order.getPurchaseOrderType()==EnumPurchaseOrderType.PSIKOTROPIKA){
			prefiks=setting.getSetting("psikotropika").getSettingValue();
			purchaseNumber=getPurchaseLastNumber("PSIKOTROPIKA");			
		}else if(order.getPurchaseOrderType()==EnumPurchaseOrderType.NARKOTIKA){
			prefiks=setting.getSetting("narkotika").getSettingValue();
			purchaseNumber=getPurchaseLastNumber("NARKOTIKA");			
		}else{
			prefiks=setting.getSetting("general").getSettingValue();
			purchaseNumber=getPurchaseLastNumber("GENERAL");
		}
		if(currentNumber==0){
			purchaseNumber=purchaseNumber+1;
		}else{
			purchaseNumber=currentNumber+1;
		}

		return prefiks+"/"+month+"/"+year+"/"+purchaseNumber;
	}
	private int getPurchaseOrderNumber(String input){
		int currentNumber;
		String[] purchaseNumber=input.split("/");	
		int length=purchaseNumber.length-1;
		currentNumber=Integer.parseInt(purchaseNumber[length]); //ambil nomor saat ini;
		return currentNumber; //belum ditambah satu ya...		
	}
	private int getPurchaseLastNumber(String purchaseOrderType){
		try {
			PurchaseOrder newest=server.find(PurchaseOrder.class).
					where().eq("purchaseOrderType",purchaseOrderType).order().desc("timestamp").findList().get(0);		
			int currentNumber;
			String[] purchaseNumber=newest.getPurchaseOrderNumber().split("/");	
			int length=purchaseNumber.length-1;
			currentNumber=Integer.parseInt(purchaseNumber[length]); //ambil nomor saat ini;
			return currentNumber; //belum ditambah satu ya...
		} catch (Exception e) {
			return 0;
		}
	}
	
	List<Integer> reqPlanningList;  //menyimpan daftar rencana kebutuhan, 
	//digunakan untuk mengganti status rencana kebutuhan, bahwa sudah dibuatkan purchase order
	
	//menghasilkan obyek purchase order dari req planning
	//Disini dilakukan pemisahan antar tiap supplier dan jenis obat
	public List<PurchaseOrder> generatePurchaseOrder(FormData data){
		int psikotropikaNumber=0;
		int narkotikaNumber=0;
		int generalNumber=0;
		try {
			List<Integer> reqPlanningIds=data.getReqPlanningId();
			
			reqPlanningList = data.getReqPlanningId();  //untuk fungsi update status reqPlanning
			
			Date purchaseDate=data.getPurchaseDate(); 
			int IdUser=data.getIdUser();
			
			List<ReqPlanning> reqPlannings=new ArrayList<ReqPlanning>();
			for(Integer id:reqPlanningIds){
				reqPlannings.add(server.find(ReqPlanning.class, id));
			}
			List<PurchaseOrder> list=new ArrayList<PurchaseOrder>();
			Map<Integer, PurchaseOrder> poGeneral=new HashMap<Integer, PurchaseOrder>();
			for(ReqPlanning reqPlanning:reqPlannings){
				EnumGoodsType goodsType=reqPlanning.getSupplierGoods().getGoods().getType();
				
				if(goodsType==EnumGoodsType.NARKOTIKA){
					PurchaseOrder order=new PurchaseOrder();
					order.setDate(purchaseDate);
					order.setPurchaseOrderType(EnumPurchaseOrderType.NARKOTIKA);
					
					String numberPO=generatePurchaseOrderNumber(order, narkotikaNumber);
					order.setPurchaseOrderNumber(numberPO);
					narkotikaNumber=getPurchaseOrderNumber(numberPO);
					
					order.setUserResponsible(server.find(User.class, IdUser));
					order.setRayon(setting.getSetting("rayon").getSettingValue());
					//tanggal
					//tipe
					//order
					//jumlah barang
					
					PurchaseOrderItem item=new PurchaseOrderItem();
					item.setPurchaseOrder(order);
					item.setSupplierGoods(reqPlanning.getSupplierGoods());
					item.setQuantity(reqPlanning.getAcceptedQuantity());
					item.setInformation(reqPlanning.getInformation());
					
					
					List<PurchaseOrderItem> itemList=new ArrayList<PurchaseOrderItem>();
					itemList.add(item);
					
					order.setPurchaseOrderItem(itemList);
					order.setPurchaseOrderName(generateName(order));

					list.add(order);
				}else{
					//jika bukan psikotoprika dan narkotika, maka dikelompokan berdasarkan supplier
					int mapKey=reqPlanning.getSupplierGoods().getSupplier().getIdSupplier();
					PurchaseOrderItem item=new PurchaseOrderItem();
					item.setSupplierGoods(reqPlanning.getSupplierGoods());
					item.setQuantity(reqPlanning.getAcceptedQuantity());
					item.setInformation(reqPlanning.getInformation());
					
					//jika purchase order untuk supplier ini belum ditambahkan 
					if(!poGeneral.containsKey(mapKey)){
						PurchaseOrder order=new PurchaseOrder();
						if(goodsType==EnumGoodsType.PSIKOTROPIKA){
							order.setPurchaseOrderType(EnumPurchaseOrderType.PSIKOTROPIKA);	
							String numberPO=generatePurchaseOrderNumber(order, psikotropikaNumber);
							order.setPurchaseOrderNumber(numberPO);
							psikotropikaNumber=getPurchaseOrderNumber(numberPO);
						}else{
							order.setPurchaseOrderType(EnumPurchaseOrderType.GENERAL);
							String numberPO=generatePurchaseOrderNumber(order, generalNumber);
							order.setPurchaseOrderNumber(numberPO);
							generalNumber=getPurchaseOrderNumber(numberPO);

						}
						order.setDate(purchaseDate);
						order.setUserResponsible(server.find(User.class, IdUser));
						order.setRayon(setting.getSetting("rayon").getSettingValue());


						item.setPurchaseOrder(order);
						
						List<PurchaseOrderItem> itemList=new ArrayList<PurchaseOrderItem>();
						itemList.add(item);
						
						order.setPurchaseOrderItem(itemList);
						order.setPurchaseOrderName(generateName(order));

						
						poGeneral.put(mapKey,order);
					}else{
						//jika untuk supplier di req planning udah ada, maka tinggal nambahin item
						PurchaseOrder order=poGeneral.get(mapKey);
						item.setPurchaseOrder(order);
						order.getPurchaseOrderItem().add(item);
					}
				}
				
			}
			for(Map.Entry<Integer, PurchaseOrder> entry:poGeneral.entrySet()){
				list.add(entry.getValue());
			}
			System.out.println("generatePurchaseOrder "+list.size());
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public void saveReqPlanning(){
		try {
			if(reqPlanningList!=null){
				for(Integer i:reqPlanningList){
					ReqPlanning reqPlanning=server.find(ReqPlanning.class, i);
					reqPlanning.setPurchaseOrderCreated(true);
					 
					server.save(reqPlanning);
				}
			}
		} catch (Exception e) {
			// Nothing TODO
		}
	}
	public String savePurchaseOrder(List<PurchaseOrder> orders){
		server.beginTransaction();
		try {
			for(PurchaseOrder order:orders){
				saveSinglePurchaseOrder(order);
			}
			saveReqPlanning();
			server.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			server.rollbackTransaction();
			return "Gagal Menyimpan surat pesanan";
		}finally{
			server.endTransaction();
		}
		
		return null;
	}
	//pastikan nomor purchase order di update lagi
	public void saveSinglePurchaseOrder(PurchaseOrder order) throws Exception{
		PurchaseOrderItem item;
		order.setTimestamp(new Date());
		
		order.setPurchaseOrderName(generateName(order));
		
		server.save(order);
	}
	private String generateName(PurchaseOrder purchaseOrder){
		String goodsType=getPurchaseType(purchaseOrder);
		String supplierName=getSupplierName(purchaseOrder);
		String date=function.getDate().dateToText(purchaseOrder.getDate());
		return "Surat Pesanan "+goodsType+", bulan "+date+","
				+ " Untuk Distributor "+supplierName;
	}
	
	
	private EnumGoodsType getPurchaseTypeEnum(PurchaseOrder purchaseOrder){
		return purchaseOrder.getPurchaseOrderItem().
				get(0).getSupplierGoods().getGoods().getType();		
	}
	private String getPurchaseType(PurchaseOrder purchaseOrder){
		String goodsType;
		try {
			EnumGoodsType enumGoodsType=purchaseOrder.getPurchaseOrderItem().
					get(0).getSupplierGoods().getGoods().getType();
			if(enumGoodsType==EnumGoodsType.NARKOTIKA ||
					enumGoodsType==EnumGoodsType.PSIKOTROPIKA){
				goodsType=enumGoodsType.toString().toLowerCase();
			}else{
				goodsType="Biasa";
			}
		} catch (Exception e) {
			goodsType="Biasa";
		}
		return goodsType;
	}
	private String getSupplierName(PurchaseOrder purchaseOrder){
		String supplierName;
		try {
			supplierName=purchaseOrder.getPurchaseOrderItem().
					get(0).getSupplierGoods().getSupplier().getSupplierName();
		} catch (Exception e) {
			supplierName="";
		}		
		return supplierName;
	}



}
