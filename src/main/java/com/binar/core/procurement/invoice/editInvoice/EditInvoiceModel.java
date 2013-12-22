package com.binar.core.procurement.invoice.editInvoice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.OptimisticLockException;

import com.avaje.ebean.EbeanServer;
import com.binar.core.procurement.invoice.newInvoice.NewInvoiceModel;
import com.binar.core.procurement.invoice.newInvoice.NewInvoiceView.FormData;
import com.binar.entity.Invoice;
import com.binar.entity.SupplierGoods;
import com.binar.generalFunction.GeneralFunction;

public class EditInvoiceModel {

	private NewInvoiceModel newInvoiceModel;
	private GeneralFunction function;
	private EbeanServer server;
	
	public EditInvoiceModel(GeneralFunction function) {
		newInvoiceModel=new NewInvoiceModel(function);
		this.function=function;
		this.server=function.getServer();
	}
	public NewInvoiceModel getNewInvoiceModel() {
		return newInvoiceModel;
	}
	public Invoice getInvoice(int invoiceId){
		return server.find(Invoice.class, invoiceId);
	}
	public List<String> saveInvoice(FormData data){
		List<String> error=data.validate();
		if(error!=null){
			return error;
		}else{
			error=new ArrayList<String>();
			Invoice invoice=newInvoiceModel.formDataToInvoice(data);
			server.beginTransaction();
			try {
				server.update(invoice);
				//update harga supplier
				for(com.binar.entity.InvoiceItem item:invoice.getInvoiceItem()){
					SupplierGoods supplier=item.getPurchaseOrderItem().getSupplierGoods();
					supplier.setLastUpdate(new Date());
					supplier.setLastPrice(item.getPricePPN()); //PRICE PPN 
					server.update(supplier);
				}
				server.commitTransaction();
				return null;
			} catch (OptimisticLockException e) {
				error.add("Kesalahan penyimpanan data " +e.getMessage());
				e.printStackTrace();
				server.rollbackTransaction();
				return error;
			}finally{
				server.endTransaction();
			}
			
		}
	}
}
