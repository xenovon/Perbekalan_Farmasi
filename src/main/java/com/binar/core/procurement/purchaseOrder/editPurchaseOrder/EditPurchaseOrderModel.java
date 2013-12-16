package com.binar.core.procurement.purchaseOrder.editPurchaseOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.ReqPlanning;
import com.binar.entity.Role;
import com.binar.entity.User;
import com.binar.generalFunction.GeneralFunction;

public class EditPurchaseOrderModel {
	
	GeneralFunction function;
	EbeanServer server;
	
	public EditPurchaseOrderModel(GeneralFunction function) {
		this.function=function;
		server=function.getServer();
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
	
	public PurchaseOrder getPurchaseOrder(int id){
		return server.find(PurchaseOrder.class, id);
	}
	public String updatePurchaseOrder(FormData data){
		try {
			PurchaseOrder order=server.find(PurchaseOrder.class, data.getIdPurchaseOrder());
			order.setDate(data.getDate());
			order.setPurchaseOrderName(data.getName());
			order.setPurchaseOrderNumber(data.getNumber());
			order.setRayon(data.getRayon());
			order.setUserResponsible(server.find(User.class, data.getUserResponsible()));
			
			server.update(order);
			return null;
		} catch (Exception e) {
			return "kesalahan saat menyimpan data : "+ e.getMessage();
		}
	}

}
