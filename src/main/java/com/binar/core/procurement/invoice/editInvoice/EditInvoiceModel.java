package com.binar.core.procurement.invoice.editInvoice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.OptimisticLockException;

import com.avaje.ebean.EbeanServer;
import com.binar.core.procurement.invoice.newInvoice.NewInvoiceModel;
import com.binar.core.procurement.invoice.newInvoice.NewInvoiceView.FormData;
import com.binar.core.procurement.invoice.newInvoice.NewInvoiceView.InvoiceItem;
import com.binar.entity.Invoice;
import com.binar.entity.SupplierGoods;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;

public class EditInvoiceModel {

	private NewInvoiceModel newInvoiceModel;
	private GeneralFunction function;
	private EbeanServer server;
	private GetSetting setting;
	
	public EditInvoiceModel(GeneralFunction function) {
		newInvoiceModel=new NewInvoiceModel(function);
		this.function=function;
		this.setting=function.getSetting();
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
			Invoice invoice=formDataToInvoice(data);
			int idInvoice=invoice.getIdInvoice();
			try {
				server.update(invoice);
				//update harga supplier
				Invoice invoice2=getInvoice(idInvoice);
				for(com.binar.entity.InvoiceItem item:invoice2.getInvoiceItem()){
					SupplierGoods supplier=item.getPurchaseOrderItem().getSupplierGoods();
					supplier.setLastUpdate(new Date());
					supplier.setLastPrice(item.getPricePPN()); //PRICE PPN 
					server.update(supplier);
				}
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
	public Invoice formDataToInvoice(FormData data){
		Invoice invoice=getInvoice(data.getiDInvoice());
		List<com.binar.entity.InvoiceItem> invoiceItem=invoice.getInvoiceItem();
		invoice.setAmountPaid(data.getAmountPaidDouble());
		invoice.setDuedate(data.getDueDate());
		invoice.setInvoiceNumber(data.getInvoiceNumber());
		invoice.setTimestamp(new Date());
		invoice.setInvoiceDate(data.getInvoiceDate());
		for(InvoiceItem item:data.getInvoiceItems()){
			com.binar.entity.InvoiceItem invo=server.find(com.binar.entity.InvoiceItem.class, item.getIdInvoiceItem());
			invo.setBatch(item.getBatch());
			invo.setDiscount(item.getdiscountDouble());
			invo.setInvoice(invoice);
			invo.setExpiredDate(item.getExpiredDate());
			if(item.isPPN()){
				invo.setPricePPN(item.getPriceDouble());
				double price=(100/(100+setting.getPPN())*item.getPriceDouble());
				invo.setPrice(price);
			}else{
				invo.setPrice(item.getPriceDouble());
				double pricePPN=(item.getPriceDouble()+(item.getPriceDouble()*setting.getPPN()/100));
				invo.setPricePPN(pricePPN);
			}
			
			invo.setPurchaseOrderItem(item.getPurchaseOrderItem());
			invo.setQuantity(item.getQuantityInt());
			invo.setTotalPrice(item.getTotalPriceDouble());
			
			invoiceItem.add(invo);
		}
		invoice.setInvoiceItem(invoiceItem);
		invoice.setInvoiceName(data.getInvoiceName());
		return invoice;
	}

}
