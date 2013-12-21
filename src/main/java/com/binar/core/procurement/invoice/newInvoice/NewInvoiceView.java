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
		public double countPrice(boolean ppn, String quantity, String price);
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
	class FormData{
		private String invoiceNumber;
		private Date dueDate;
		private Date invoiceDate;
		private String amountPaid;
		private List<InvoiceItem> invoiceItems;
		
		public List<String> validate(){
			
			List<String> error=new ArrayList<String>();
			if(invoiceNumber==null){
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
	
	class InvoiceItem{
		private String batch="";
		private String discount="0";
		private String price="0";
		private String quantity="0";
		private boolean isPPN=false;
		private String totalPrice="0";
		private PurchaseOrderItem purchaseOrderItem;
		
		public List<String> validate(){
			List<String> data=new ArrayList<String>();
			data.add("<b>Pesan Untuk obat "+purchaseOrderItem.getSupplierGoods().getGoods().getName()+"</b>");
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
			if(data.size()>=1){
				return null;
			}else{
				return data;
			}
		}
		
		public double getdiscountDouble(){
			if(discount!=null){
				return Double.parseDouble(discount);				
			}else{
				return 0;
			}
		}
		public double getPriceDouble(){
			if(price!=null){
				return Double.parseDouble(price);				
			}else{
				return 0;
			}
		}
		public int getQuantityInt(){
			if(quantity!=null){
				return Integer.parseInt(quantity);				
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
		
	}

}
