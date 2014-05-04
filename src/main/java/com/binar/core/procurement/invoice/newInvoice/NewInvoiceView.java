package com.binar.core.procurement.invoice.newInvoice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.binar.core.procurement.purchaseOrder.newPurchaseOrder.NewPurchaseOrderView.FormViewEnum;
import com.binar.entity.Invoice;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.PurchaseOrderItem;


public interface NewInvoiceView {
	
	public enum FormViewEnum{
		INPUT_VIEW, EDIT_VIEW
	}
	
	public interface NewInvoiceListener{
		public void buttonClick(String button);
		public void periodChange();
		public double countPrice(boolean ppn, String quantity, String price,
				String discount);
	}
	
	public void init();
	public void construct();
	public void setListener(NewInvoiceListener listener);
	public Date[] getSelectedPeriod();
	public void setFormView(FormViewEnum formView);
	
	public void setComboPurchaseOrder(Map<Integer, String> data);
	public void generateInvoiceView(PurchaseOrder data);
	public FormData getEditedInvoice();
	public void resetForm();

	//form data untuk validasi
	public class FormData{
		private int iDInvoice;
		private String invoiceNumber;
		private Date dueDate;
		private Date invoiceDate;
		private String amountPaid;
		private String invoiceName;
		private List<InvoiceItem> invoiceItems;
		
		public int getiDInvoice() {
			return iDInvoice;
		}
		public void setiDInvoice(int iDInvoice) {
			this.iDInvoice = iDInvoice;
		}
		public String getInvoiceName() {
			return invoiceName;
		}
		public void setInvoiceName(String invoiceName) {
			this.invoiceName = invoiceName;
		}
		public List<String> validate(){
			
			List<String> error=new ArrayList<String>();
			if(dueDate==null){  
				error.add("Format tanggal salah");
			}
			if(invoiceDate==null){
				error.add("Format tanggal salah");
			}
			
			if(invoiceNumber==null || invoiceNumber.equals("")){
				error.add("Nomor faktur tidak boleh kosong");
			}
			try {
				double amount=getAmountPaidDouble();
				if(amount<0){
					error.add("Jumlah dibayar tidak boleh dibawah 0");
				}
			} catch (NumberFormatException e) {
				error.add("Jumlah dibayar harus berupa angka");
			}
			for(InvoiceItem item:invoiceItems){
				List<String> errorInvoice=item.validate();
				if(errorInvoice!=null){
					for(String string:errorInvoice){
						error.add(string);
					}
				}
				
			}
			if(error.size()==0){
				return null;
			}else{
				return error;
			}
		}
		public Date getInvoiceDate() {
			return invoiceDate;
		}
		public void setInvoiceDate(Date invoiceDate) {
			this.invoiceDate = invoiceDate;
		}
		public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
			this.invoiceItems = invoiceItems;
		}
		public double getAmountPaidDouble(){
			return Double.parseDouble(amountPaid);
		}
		public String getInvoiceNumber() {
			return invoiceNumber;
		}
		public void setInvoiceNumber(String invoiceNumber) {
			this.invoiceNumber = invoiceNumber;
		}
		public Date getDueDate() {
			return dueDate;
		}
		public void setDueDate(Date dueDate) {
			this.dueDate = dueDate;
		}
		public String getAmountPaid() {
			return amountPaid;
		}
		public void setAmountPaid(String amountPaid) {
			this.amountPaid = amountPaid;
		}

		public List<InvoiceItem> getInvoiceItems() {
			return invoiceItems;
		}
	}
	
	public class InvoiceItem{
		private int idInvoiceItem;
		private String batch="";
		private String discount="0";
		private String price="0";
		private Date expiredDate;
		private String quantity="0";
		private boolean isPPN=false;
		private String totalPrice="0";
		private PurchaseOrderItem purchaseOrderItem;
		private boolean checkSaveReceipt;
		
		public int getIdInvoiceItem() {
			return idInvoiceItem;
		}
		public void setIdInvoiceItem(int idInvoiceItem) {
			this.idInvoiceItem = idInvoiceItem;
		}
		public List<String> validate(){
			List<String> data=new ArrayList<String>();
			data.add("<b>Pesan Untuk obat "+purchaseOrderItem.getSupplierGoods().getGoods().getName()+"</b>");
			if(batch==null || batch.equals("")){
				data.add("Batch Tidak Boleh Kosong");
			}
			if(expiredDate==null){
				data.add("Format tanggal salah");
			}
			try {
				double discDouble=getdiscountDouble();
				if(discDouble>100 || discDouble <0){
					data.add("Diskon harus diantara 0 hingga 100");
				}
			} catch (NumberFormatException e) {
				data.add("Diskon harus berupa angka");
			} 
			 try {
				double priceDouble=getPriceDouble();
				if(priceDouble<0){
					data.add("Harga harus lebih dari 0");
				}
			} catch (NumberFormatException e) {
				data.add("Harga harus berupa angka");
			}
			
			try {
				double totalPriceDouble=getTotalPriceDouble();
				if(totalPriceDouble<0){
					data.add("Harga total harus lebih dari 0");
				}
			} catch (NumberFormatException e) {
				data.add("Harga total harus berupa angka");
			}
			
			try {
				int totalQuantity=getQuantityInt();
				if(totalQuantity<0){
					data.add("Jumlah harus lebih dari 0");
				}
			} catch (NumberFormatException e) {
				data.add("Jumlah harus berupa angka");
			}
			if(data.size()<=1){
				return null;
			}else{
				return data;
			}
		}
		
		public double getdiscountDouble(){
			if(discount!=null){
				if(!discount.equals("")){
					return Double.parseDouble(discount);									
				}else{
					return 0;
				}
			}else{
				return 0;
			}
		}
		public double getPriceDouble(){
			if(price!=null){
				if(!price.equals("")){
					return Double.parseDouble(price);									
				}else{
					return 0;
				}
			}else{
				return 0;
			}
		}
		public int getQuantityInt(){
			if(quantity!=null){
				if(!quantity.equals("")){
					return Integer.parseInt(quantity);				
				}else{
					return 0;
				}
			}else{
				return 0;
			}
		}
		public double getTotalPriceDouble(){
			if(totalPrice!=null){
				return Double.parseDouble(totalPrice);				
			}else{
				return 0;
			}
		}
		public void setPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
			this.purchaseOrderItem = purchaseOrderItem;
		}
		public PurchaseOrderItem getPurchaseOrderItem() {
			return purchaseOrderItem;
		}
		public Date getExpiredDate() {
			return expiredDate;
		}
		public void setExpiredDate(Date expiredDate) {
			this.expiredDate = expiredDate;
		}
		public String getBatch() {
			return batch;
		}
		public void setBatch(String batch) {
			this.batch = batch;
		}
		public String getDiscount() {
			return discount;
		}
		public void setDiscount(String discount) {
			this.discount = discount;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		public String getQuantity() {
			return quantity;
		}
		public void setQuantity(String quantity) {
			this.quantity = quantity;
		}
		public boolean isPPN() {
			return isPPN;
		}
		public void setPPN(boolean isPPN) {
			this.isPPN = isPPN;
		}
		public String getTotalPrice() {
			return totalPrice;
		}
		public void setTotalPrice(String totalPrice) {
			this.totalPrice = totalPrice;
		}
		public void setCheckSaveReceipt(boolean checkSaveReceipt) {
			this.checkSaveReceipt = checkSaveReceipt;
		}
		public boolean isCheckSaveReceipt() {
			return checkSaveReceipt;
		}
		
	}

}
