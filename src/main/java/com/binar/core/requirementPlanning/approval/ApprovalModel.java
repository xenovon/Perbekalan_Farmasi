package com.binar.core.requirementPlanning.approval;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextField;

public class ApprovalModel {
	
	GeneralFunction function;
	EbeanServer server;
	
	public class AcceptData{
		private int idReq;
		private boolean accepted;
		private int quantityAccepted;
		protected int getIdReq() {
			return idReq;
		}
		protected void setIdReq(int idReq) {
			this.idReq = idReq;
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
	
	public ApprovalModel(GeneralFunction function){
		this.function=function;
		this.server=function.getServer();
	}
	//mendapatkan tabel req planning sesuai bulan yang dipilih
	public List<ReqPlanning> getTableData(DateTime periode){
		Date startDate=periode.toDate();
		Date endDate=periode.withDayOfMonth(periode.dayOfMonth().getMaximumValue()).toDate();
		List<ReqPlanning> returnValue=server.find(ReqPlanning.class).where().
				between("period", startDate, endDate).findList();

		return returnValue;
	}
	//mendapatkan data tabel yang mau disimpan
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
			acceptData.setIdReq((Integer)itemId);
			acceptData.setQuantityAccepted(quantityAccepted);
			
			returnValue.add(acceptData);
		}
		
		return returnValue;
	}

	//untuk menerima kebutuhan
	public String acceptData(Container container){
		List<AcceptData> acceptData=getTableData(container);
		if(acceptData==null){
			return "Jumlah kebutuhan harus berupa angka";
		}
		//mulai transaksi
		server.beginTransaction();

		try {
			for(AcceptData data:acceptData){
				ReqPlanning planning=server.find(ReqPlanning.class, data.getIdReq());
				planning.setAcceptedQuantity(data.getQuantityAccepted());
				planning.setAccepted(data.isAccepted());
				server.update(planning);
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
	
	public ReqPlanning getReqPlanning(int idReq){
		return server.find(ReqPlanning.class, idReq);
	}
	
	

}
