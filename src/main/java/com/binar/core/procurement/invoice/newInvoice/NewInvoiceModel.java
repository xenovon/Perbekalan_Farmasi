package com.binar.core.procurement.invoice.newInvoice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.core.inventoryManagement.receptionList.inputReception.FormReception;
import com.binar.core.inventoryManagement.receptionList.inputReception.InputReceptionModel;
import com.binar.core.procurement.invoice.newInvoice.NewInvoiceView.FormData;
import com.binar.core.procurement.invoice.newInvoice.NewInvoiceView.InvoiceItem;
import com.binar.entity.Goods;
import com.binar.entity.GoodsReception;
import com.binar.entity.Invoice;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.PurchaseOrderItem;
import com.binar.entity.SupplierGoods;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;

public class NewInvoiceModel {
	
	GeneralFunction function;
	EbeanServer server;
	GetSetting setting;
	DateManipulator date;
	InputReceptionModel receptionModel;
	
	public NewInvoiceModel(GeneralFunction function) {
		this.function=function;
		receptionModel=new InputReceptionModel(function);
		this.server=function.getServer();
		this.setting=function.getSetting();
		this.date=function.getDate();
	}

	public List<PurchaseOrder> getPurchaseOrderList(Date dateStart, Date dateEnd){
		try {
			List<PurchaseOrder> returnValue=server.find(PurchaseOrder.class).
					where().between("date", dateStart, dateEnd).findList();
			if(returnValue.size()==0){
				return null;
			}else{
				return returnValue;
			}
		} catch (Exception e) {
			return null;
		}
	}
	/*  
	 * (non-Javadoc)
	 * @see com.binar.core.procurement.invoice.InvoiceView#init()
	 *  INVOICE
		idInvoice
		Nomor invoice
		nama invoice
		jatuh tempo
		timestamp
		total tagihan
		jumlah dibayar
		jumlah item
		
		
		INVOICE ITEM
		invoice
		batch
		discount
		price
		pricePPN
		quantity
		purchaseOrderItem
	 */	

	public PurchaseOrder getPurchaseOrder(int id){
		return server.find(PurchaseOrder.class, id);
	}
	public FormData generateInvoiceFormData(PurchaseOrder order){
		FormData formData=new FormData();
		
		List<InvoiceItem> invoiceItems=new ArrayList<NewInvoiceView.InvoiceItem>();
		for(PurchaseOrderItem item:order.getPurchaseOrderItem()){
			InvoiceItem invoiceItem=new InvoiceItem();
			invoiceItem.setQuantity(item.getQuantity()+"");
			invoiceItem.setPurchaseOrderItem(item);
			
			invoiceItems.add(invoiceItem);
		}
		formData.setInvoiceItems(invoiceItems);
		return formData;
	}
	public List<String> saveReception(com.binar.entity.InvoiceItem item){
		FormReception formReception=new FormReception(function);
		formReception.setReceptionDate(item.getInvoice().getInvoiceDate());
		formReception.setQuantity(item.getQuantity()+"");
		formReception.setExpiredDate(item.getExpiredDate());
		formReception.setInformation("Dari Invoice "+item.getInvoice().getInvoiceNumber());
		formReception.setInvoiceItemId(item.getIdInvoiceItem());
		
		return receptionModel.insertData(formReception);
	}
	public List<String> saveInvoice(FormData data){
		List<String> error=data.validate();
		if(error!=null){
			return error;
		}else{
			Invoice invoice=formDataToInvoice(data);
			error=new ArrayList<String>();
			try {
				server.save(invoice);
				//update harga supplier
				for(com.binar.entity.InvoiceItem item:invoice.getInvoiceItem()){
					SupplierGoods supplier=item.getPurchaseOrderItem().getSupplierGoods();
					supplier.setLastUpdate(new Date());
					supplier.setLastPrice(item.getPricePPN()); //PRICE PPN 
					server.update(supplier);
				}
				for(com.binar.entity.InvoiceItem item:invoice.getInvoiceItem()){
					if(item.isSaveToReceipt()){
						List<String> result=saveReception(item);
						if(result!=null){
							error.addAll(result);
						}
					}
				}				
				return null;
			} catch (OptimisticLockException e) {
				error.add("Kesalahan penyimpanan data " +e.getMessage());
				e.printStackTrace();
				return error;
			}
		}
	}
	
	public Invoice formDataToInvoice(FormData data){
		Invoice invoice=new Invoice();
		List<com.binar.entity.InvoiceItem> invoiceItem=new ArrayList<com.binar.entity.InvoiceItem>();
		invoice.setAmountPaid(data.getAmountPaidDouble());
		invoice.setDuedate(data.getDueDate());
		invoice.setInvoiceNumber(data.getInvoiceNumber());
		invoice.setTimestamp(new Date());
		invoice.setInvoiceDate(data.getInvoiceDate());
		for(InvoiceItem item:data.getInvoiceItems()){
			com.binar.entity.InvoiceItem invo=new com.binar.entity.InvoiceItem();
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
			invo.setSaveToReceipt(item.isCheckSaveReceipt());
			
			invoiceItem.add(invo);
			
		}
		invoice.setInvoiceItem(invoiceItem);
		invoice.setInvoiceName(generateInvoiceName(invoice));
		return invoice;
	}
	
	private String generateInvoiceName(Invoice invoice){
		String supplierName=invoice.getInvoiceItem().get(0).
				getPurchaseOrderItem().getSupplierGoods().getSupplier().getSupplierName();
		String invoiceDate=date.dateToText(invoice.getInvoiceDate(), true);
		return "Faktur "+supplierName+" tanggal "+invoiceDate+"";
	}
	public double countPrice(boolean isPPN, double price, double quantity){
		if(isPPN){
			return price*quantity;
		}else{
			double pricePPN=price+(setting.getPPN()/100*price);
			return pricePPN*quantity;
		}
	}
	public double countTotalPrice(int quantity, double pricePPN, double discount){
		double total=quantity*pricePPN;
		double minusDiscount=total-(total*discount/100);
		return minusDiscount;
	}
}
