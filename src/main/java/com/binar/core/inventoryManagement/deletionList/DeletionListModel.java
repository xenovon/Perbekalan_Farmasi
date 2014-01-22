package com.binar.core.inventoryManagement.deletionList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.core.inventoryManagement.deletionList.DeletionListView.ApprovalFilter;
import com.binar.entity.DeletedGoods;
import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.binar.generalFunction.GeneralFunction;

public class DeletionListModel {
	
	GeneralFunction generalFunction;
	EbeanServer server;
	
	public DeletionListModel (GeneralFunction function) {
		this.generalFunction=function;
		server=generalFunction.getServer();
	}
	
	public List<DeletedGoods> getDeletedTable(Date startDate, Date endDate, ApprovalFilter filter ){
		try {
			DateTime start=new DateTime(startDate);
			start=start.withHourOfDay(start.hourOfDay().getMinimumValue());
			DateTime end=new DateTime(endDate);
			end=end.withHourOfDay(end.hourOfDay().getMaximumValue());
			if(start.compareTo(end)>0){
				DateTime buffer=start;
				start=end;
				end=buffer;
			}
			System.out.println("Start "+start.toString()+" end "+end.toString() );
			switch (filter) {
				case ACCEPTED: return server.find(DeletedGoods.class).where().between("deletionDate", start.toDate(), end.toDate()).eq("isAccepted", true).findList(); 
				case ALL: return server.find(DeletedGoods.class).where().between("deletionDate", start.toDate(), end.toDate()).findList();
				case NON_ACCEPTED: return server.find(DeletedGoods.class).where().between("deletionDate", start.toDate(), end.toDate()).eq("isAccepted", false).findList();
				default: return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<DeletedGoods>();
		}
		
	}
	public DeletedGoods getSingleDeletedGoods(int idGoods){
		return server.find(DeletedGoods.class, idGoods);
	}
	public String deleteDeletionGoods(int idDeletion){
		try {
			DeletedGoods deleted=server.find(DeletedGoods.class, idDeletion);
			server.delete(deleted);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return "Kesalahan menghapus data "+e.getMessage();
		}
	}

}
