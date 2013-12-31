package com.binar.core.inventoryManagement.deletionList.deletionApproval;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.core.requirementPlanning.approval.ApprovalModel.AcceptData;
import com.binar.entity.DeletedGoods;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextField;

public class DeletionApprovalModel {
	
	GeneralFunction function;
	EbeanServer server;
	
	public class AcceptData{
		private int idDel;
		private boolean accepted;
		private int quantityAccepted;
		protected int getIdDel() {
			return idDel;
		}
		protected void setIdDel(int idDel) {
			this.idDel = idDel;
		}
		protected boolean isAccepted() {
			return accepted;
		}
		protected void setAccepted(boolean accepted) {
			this.accepted = accepted;
		}
		protected int getQuantityAccepted() {
			return quantityAccepted;
		}
		protected void setQuantityAccepted(int quantityAccepted) {
			this.quantityAccepted = quantityAccepted;
		}
		
	}
	
	public DeletionApprovalModel(GeneralFunction function){
		this.function=function;
		this.server=function.getServer();
	}
	
	public List<DeletedGoods> getTableData(DateTime periode){
		Date startDate=periode.toDate();
		Date endDate=periode.withDayOfMonth(periode.dayOfMonth().getMaximumValue()).toDate();
		List<DeletedGoods> returnValue=server.find(DeletedGoods.class).where().
				between("deletionDate", startDate, endDate).findList();
		return returnValue;
	}
	
	private List<AcceptData> getTableData(Container container) {
		List<AcceptData> returnValue=new ArrayList<AcceptData>();
		Collection<?> tableItemId=container.getItemIds();
		for(Object itemId:tableItemId){
			Item item=container.getItem(itemId);
			
			AcceptData acceptData=new AcceptData();
			CheckBox checkboxResult=(CheckBox)item.getItemProperty("Disetujui?").getValue();
			TextField textfieldResult=(TextField)item.getItemProperty("Jumlah Disetujui").getValue();
			int quantityAccepted;
			try {
				quantityAccepted=Integer.parseInt(textfieldResult.getValue());
				
			} catch (NumberFormatException e) {
				//jika ada field yang tidak sesuai format misalnya ada teksnya, maka langsung return null
				//maka nantinya nampilin pesan error
				return null;
			}
			acceptData.setAccepted(checkboxResult.getValue());
			acceptData.setIdDel((Integer)itemId);
			acceptData.setQuantityAccepted(quantityAccepted);
			
			returnValue.add(acceptData);
		}
		
		return returnValue;
	}
	
	public String acceptData(Container container){
		List<AcceptData> acceptData=getTableData(container);
		if(acceptData==null){
			return "Jumlah harus berupa angka";
		}
		//mulai transaksi
		server.beginTransaction();

		try {
			for(AcceptData data:acceptData){
				DeletedGoods delGoods=server.find(DeletedGoods.class, data.getIdDel());
				delGoods.setAccepted(data.isAccepted());
				server.update(delGoods);
			}
			server.commitTransaction();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			server.rollbackTransaction();
			return e.getMessage();
		}finally{
			server.endTransaction();
		}		
	}
	
	public DeletedGoods getDeletedGoods(int idDel){
		return server.find(DeletedGoods.class, idDel);
	}
}
