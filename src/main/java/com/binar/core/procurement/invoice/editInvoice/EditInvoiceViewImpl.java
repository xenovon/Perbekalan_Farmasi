package com.binar.core.procurement.invoice.editInvoice;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;

import com.binar.core.procurement.invoice.newInvoice.NewInvoiceView;
import com.binar.core.procurement.invoice.newInvoice.NewInvoiceView.FormData;
import com.binar.core.procurement.invoice.newInvoice.NewInvoiceView.FormViewEnum;
import com.binar.core.procurement.invoice.newInvoice.NewInvoiceView.InvoiceItem;
import com.binar.core.procurement.invoice.newInvoice.NewInvoiceView.NewInvoiceListener;
import com.binar.entity.Invoice;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.PurchaseOrderItem;
import com.binar.generalFunction.GeneralFunction;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class EditInvoiceViewImpl extends VerticalLayout implements EditInvoiceView, ClickListener, ValueChangeListener{

	GeneralFunction function;
	public EditInvoiceViewImpl(GeneralFunction function) {
		this.function=function;
	}
	
	class InvoiceItemForm extends FormLayout implements ValueChangeListener {
		
		private Label title;
		private TextField inputBatch;
		private TextField inputDiscount;
		private TextField inputPrice;
		private TextField inputQuantity;
		private CheckBox isPPN;
		private DateField expiredDate;
		private TextField totalPrice;
		private com.binar.entity.InvoiceItem invoiceItem;
		
		GeneralFunction function;
		
		EditInvoiceListener listener;
		public InvoiceItemForm(GeneralFunction function,
				com.binar.entity.InvoiceItem item, EditInvoiceListener listener, ValueChangeListener valueListener) {
			this.invoiceItem=item;
			this.listener=listener;
			this.function=function;
			String goodsName=item.getPurchaseOrderItem().getSupplierGoods().getGoods().getName();
			String quantity=item.getQuantity()+"";
			String price=item.getPricePPN()+"";
			totalPrice=new TextField("Total Harga Barang");
				totalPrice.setImmediate(true);
				totalPrice.addValueChangeListener(valueListener);
		
			title=new Label("<h3>Isi faktur untuk barang "+goodsName+"</h3>", ContentMode.HTML);
			inputBatch=new TextField("Batch");
				inputBatch.setImmediate(true);
			inputDiscount =new TextField("Diskon");
				inputDiscount.setImmediate(true);
				inputDiscount.setDescription("Diskon dalam %, antara 0-100");
				inputDiscount.addValueChangeListener(this);
				inputDiscount.addValueChangeListener(valueListener);
				
			inputPrice=new TextField("Harga Barang");
				inputPrice.setImmediate(true);
				inputPrice.setDescription("Harga barang ini");
				inputPrice.addValueChangeListener(this);
				inputPrice.addValueChangeListener(valueListener);
				inputPrice.setValue(price);
				inputQuantity=new TextField("Kuantitas");
				inputQuantity.setImmediate(true);
				inputQuantity.addValueChangeListener(this);
				inputQuantity.addValueChangeListener(valueListener);
				inputQuantity.setValue(quantity);
			expiredDate=new DateField("Kadaluarsa");
				expiredDate.setImmediate(true);
				expiredDate.addValueChangeListener(valueListener);
				//4 tahun dari sekarang
				expiredDate.setValue(new DateTime().withYear(new DateTime().getYear()+4).toDate());
			isPPN=new CheckBox("Harga Termasuk PPN");
				isPPN.setValue(true);
				isPPN.addValueChangeListener(this);
				isPPN.addValueChangeListener(valueListener);
				
			construct(item);
		}
		
		
		private void construct(com.binar.entity.InvoiceItem item){
			String goodsName=item.getPurchaseOrderItem().getSupplierGoods().getGoods().getName();
			String quantity=item.getQuantity()+"";
			String price=item.getPricePPN()+"";
			title.setValue("<h3>Isi faktur untuk barang "+goodsName+"</h3>");
			inputBatch.setValue(item.getBatch());
			inputDiscount.setValue(function.formatDecimal(item.getDiscount()));
			inputPrice.setValue(function.formatDecimal(item.getPricePPN()));
			inputQuantity.setValue(item.getQuantity()+"");
			isPPN.setValue(true);
			expiredDate.setValue(item.getExpiredDate());
			totalPrice.setValue(function.formatDecimal(item.getTotalPrice()));
			this.addComponents(title, inputBatch,inputDiscount,
					inputPrice, expiredDate, inputQuantity, isPPN, totalPrice);
		}
		@Override
		public void valueChange(ValueChangeEvent event) {
			try {
				totalPrice.setValue(function.formatDecimal(listener.countPrice(isPPN.getValue(),
						inputQuantity.getValue(), inputPrice.getValue(), inputDiscount.getValue())));
			} catch (Exception e) {
				totalPrice.setValue("0");
			}
		}
		public InvoiceItem getData(){
			InvoiceItem returnValue=new InvoiceItem();
			returnValue.setBatch(inputBatch.getValue());
			returnValue.setDiscount(inputDiscount.getValue());
			returnValue.setPPN(isPPN.getValue());
			returnValue.setPrice(inputPrice.getValue());
			returnValue.setPurchaseOrderItem(invoiceItem.getPurchaseOrderItem());
			returnValue.setQuantity(inputQuantity.getValue());
			returnValue.setTotalPrice(totalPrice.getValue());
			returnValue.setExpiredDate(expiredDate.getValue());
			return returnValue;
		}
	}

		
	@Override
	public void init() {
		buttonCancel =new Button("Batal");
		buttonCancel.addClickListener(this);
		
		buttonSubmit=new Button("Simpan Perubahan");
		buttonSubmit.addClickListener(this);
		
		labelError=new Label();
		labelError.setVisible(false);
		labelError.addStyleName("form-error");
		labelError.setContentMode(ContentMode.HTML);
		construct();
	}
	@Override
	public void construct() {
		setMargin(true);
		this.setSpacing(true);
		
	}

	@Override
	public FormData getEditedInvoice() {
		FormData data=new FormData();
		data.setAmountPaid(inputAmountPaid.getValue());
		data.setDueDate(inputDueDate.getValue());
		data.setInvoiceDate(inputInvoiceDate.getValue());
		data.setInvoiceNumber(inputInvoiceNumber.getValue());
		
		List<InvoiceItem> items=new ArrayList<NewInvoiceView.InvoiceItem>();
		Iterator<Component> iterator=this.iterator();
		while(iterator.hasNext()){
			Component component=iterator.next();
			try {
				InvoiceItemForm invoiceForm = (InvoiceItemForm)component;
				items.add(invoiceForm.getData());
			} catch (Exception e) {
				System.out.println("Do nothing");
			}
			System.out.println("iterator test");
		}
		System.out.println("Ukuran item" +items.size());
		data.setInvoiceItems(items);
		return data;
	}

	@Override
	public void resetForm() {
		this.removeAllComponents();		
		inputInvoiceNumber.setValue("");
		inputDueDate.setValue(new Date());
		inputInvoiceDate.setValue(new Date());
		inputAmountPaid.setValue("");
		
	}
	public void showError(String content){
		labelError.setValue(content);
		labelError.setVisible(true);
	}
	public void hideError(){
		labelError.setVisible(false);
	}
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonCancel){
			listener.buttonClick("buttonCancel");
		}else if(event.getButton()==buttonSubmit){
			listener.buttonClick("buttonSubmit");
		}
	}
	@Override
	public void valueChange(ValueChangeEvent event) {
		listener.valueChange();
		
	}
	EditInvoiceListener listener;
	
	@Override
	public void setListener(EditInvoiceListener listener) {
		this.listener=listener;
	}
	//Form untuk edit invoice
	private Label labelError;
	private TextField inputInvoiceNumber;
	private DateField inputDueDate;
	private DateField inputInvoiceDate;
	private TextField inputAmountPaid;
	private TextField inputInvoiceName;
	private Button buttonCancel;
	private Button buttonSubmit;
	private FormLayout invoiceFormLayout;
	@Override
	public void generateInvoiceView(Invoice data) {
		this.removeAllComponents();
		if(invoiceFormLayout==null){
			inputInvoiceNumber =new TextField("Nomor Faktur"){
				{
					setImmediate(true);
				}
			};
			inputInvoiceNumber.addValueChangeListener(this);

			inputInvoiceName=new TextField("Nama Invoice"){
				{
					setImmediate(true);
					
				}
			};
			inputInvoiceName.addValueChangeListener(this);

			inputInvoiceDate=new DateField("Tanggal Faktur"){
				{
					setImmediate(true);
					setValue(new Date());
				}
			};
			inputInvoiceDate.addValueChangeListener(this);

			inputDueDate=new DateField("Jatuh Tempo"){
				{
					setImmediate(true);
					setValue(new Date());
				}
			};
			inputDueDate.addValueChangeListener(this);

			inputAmountPaid=new TextField("Jumlah Dibayar"){
				{
					setImmediate(true);
				}
			};
			inputAmountPaid.addValueChangeListener(this);

			invoiceFormLayout =new FormLayout();
		}else{
			invoiceFormLayout.removeAllComponents();
			resetForm();
		}
		invoiceFormLayout.addComponents(inputInvoiceName, inputInvoiceNumber, inputInvoiceDate,
				inputDueDate, inputAmountPaid);

		this.addComponent(invoiceFormLayout);
		setFormData(data);

		for(com.binar.entity.InvoiceItem item:data.getInvoiceItem()){
			InvoiceItemForm form=new InvoiceItemForm(function, item, listener, this);
			this.addComponents(form);
		}
		
		this.addComponent(labelError);
		this.addComponent(new GridLayout(2, 1){
			{
				setMargin(true);
				setSpacing(true);
				addComponent(buttonSubmit, 0,0);
				addComponent(buttonCancel, 1,0);
			}
		});
	}
	@Override
	public void setFormData(Invoice data) {
		  inputInvoiceNumber.setValue(data.getInvoiceNumber());
		  inputInvoiceName.setValue(data.getInvoiceName());
		  inputDueDate.setValue(data.getDueDate());
		  inputInvoiceDate.setValue(data.getInvoiceDate());
		  inputAmountPaid.setValue(function.formatDecimal(data.getAmountPaid()));
		  
	}

}
