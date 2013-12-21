package com.binar.core.procurement.invoice.newInvoice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.binar.core.procurement.invoice.InvoiceView.InvoiceListener;
import com.binar.core.procurement.invoice.newInvoice.NewInvoiceView.InvoiceItem;
import com.binar.core.procurement.purchaseOrder.newPurchaseOrder.NewPurchaseOrderView.FormViewEnum;
import com.binar.entity.Invoice;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.PurchaseOrderItem;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class NewInvoiceViewImpl extends Window implements NewInvoiceView, ValueChangeListener, ClickListener{

	private GeneralFunction function;
	
	private FormLayout formSelectPurchaseOrder;
	private VerticalLayout viewInvoice;
	private CssLayout mainLayout; //isinya adalah button dan form/view invoice
	private GridLayout buttonLayout;
	private DateField inputRangeStart;
	private DateField inputRangeEnd;
	private ComboBox comboPurchaseOrder;
	
	private Button buttonNext;
	private Button buttonBack;
	private Button buttonCreate;
	private Button buttonCancel;
	
	private FormViewEnum position;
	
	public NewInvoiceViewImpl(GeneralFunction function) {
		this.function=function;
	}
	@Override
	public void init() {
		comboPurchaseOrder =new ComboBox("Surat Pesanan");
				comboPurchaseOrder.setImmediate(true);
				comboPurchaseOrder.addValueChangeListener(this);
		inputRangeStart =new DateField("Tanggal Mulai", new Date());
		   inputRangeStart.setImmediate(true);
		   inputRangeStart.setDateFormat("dd-MM-yyyy");	
		   inputRangeStart.addValueChangeListener(this);
		inputRangeEnd =new DateField("Tanggal Selesai", new Date());
			 inputRangeEnd.setImmediate(true);
			 inputRangeEnd.setDateFormat("dd-MM-yyyy");	
			 inputRangeEnd.addValueChangeListener(this);
		buttonNext =new Button("Selanjutnya");
			buttonNext.addClickListener(this);
		buttonBack =new Button("Kembali");
			buttonBack.addClickListener(this);
			buttonBack.setVisible(false);
		buttonCreate =new Button("Buat Faktur");
			buttonCreate.addClickListener(this);
			buttonCreate.setVisible(false);
		buttonCancel =new Button("Batalkan");
			buttonCancel.addClickListener(this);
			 

	}

	@Override
	public void construct() {
		formSelectPurchaseOrder= new FormLayout(){
			{
				setMargin(true);
				setSpacing(true);
				addComponent(new Label("<h2>Pilih Surat Pesanan</h2>", ContentMode.HTML));
				addComponent(inputRangeStart);
				addComponent(inputRangeEnd);
				addComponent(comboPurchaseOrder);
			}
		};
		
		viewInvoice =new VerticalLayout(){
			{
				setMargin(true);
				setSpacing(true);
				addComponent(new Label("<h2>Buat Invoice Baru</h2>", ContentMode.HTML));
				setVisible(false);
			}
		};
		buttonLayout=new GridLayout(4, 1){
			{
				setMargin(true);
				setSpacing(true);
				addComponent(buttonBack, 0,0);
				addComponent(buttonNext, 1, 0);
				addComponent(buttonCreate, 2, 0);
				addComponent(buttonCancel, 3, 0);
			}
		};
		mainLayout=new CssLayout(){
			{
				addComponent(formSelectPurchaseOrder);
				addComponent(viewInvoice);
				addComponent(buttonLayout);
			}
		};
		
		this.setContent(mainLayout);
		setFormView(FormViewEnum.INPUT_VIEW);
	}

	NewInvoiceListener listener;
	@Override
	public void setListener(NewInvoiceListener listener) {
		this.listener=listener;
	}

	@Override
	public Date[] getSelectedPeriod() {
		//mengembalikan array dari Date, date pertama adalah start date dan kedua adalah end date;
		return new Date[]{inputRangeStart.getValue(),inputRangeEnd.getValue()};
	}


	@Override
	public void setComboPurchaseOrder(Map<Integer, String> data) {
        for (Map.Entry<Integer, String> entry : data.entrySet()) {
        	comboPurchaseOrder.addItem(entry.getKey());
        	comboPurchaseOrder.setItemCaption(entry.getKey(), entry.getValue());
        	comboPurchaseOrder.setValue(entry.getKey());
        }
		
	}
	//kelas untuk form input invoice item.....
	class InvoiceItemForm extends FormLayout implements ValueChangeListener {
		
		private Label title;
		private TextField inputBatch;
		private TextField inputDiscount;
		private TextField inputPrice;
		private TextField inputQuantity;
		private CheckBox isPPN;
		private TextField totalPrice;
		private PurchaseOrderItem purchaseOrderItem;
		
		GeneralFunction function;
		NewInvoiceListener listener;
		public InvoiceItemForm(GeneralFunction function,
				PurchaseOrderItem item, NewInvoiceListener listener, ValueChangeListener valueListener) {
			this.purchaseOrderItem=item;
			this.listener=listener;
			this.function=function;
			String goodsName=item.getSupplierGoods().getGoods().getName();
			String quantity=item.getQuantity()+"";
			String price=item.getSupplierGoods().getLastPrice()+"";
			
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
			isPPN=new CheckBox("Harga Termasuk PPN");
				isPPN.setValue(true);
				isPPN.addValueChangeListener(this);
				isPPN.addValueChangeListener(valueListener);
			totalPrice=new TextField();
				totalPrice.setImmediate(true);
				totalPrice.addValueChangeListener(valueListener);
			construct();
		}
		private void construct(){
			this.addComponents(title, inputBatch,inputDiscount,
					inputPrice, inputQuantity, isPPN, totalPrice);
		}
		@Override
		public void valueChange(ValueChangeEvent event) {
			totalPrice.setValue(listener.countPrice(isPPN.getValue(),
					inputQuantity.getValue(), inputPrice.getValue())+"");
		}
		public InvoiceItem getData(){
			InvoiceItem returnValue=new InvoiceItem();
			returnValue.setBatch(inputBatch.getValue());
			returnValue.setDiscount(inputDiscount.getValue());
			returnValue.setPPN(isPPN.getValue());
			returnValue.setPrice(inputPrice.getValue());
			returnValue.setPurchaseOrderItem(purchaseOrderItem);
			returnValue.setQuantity(inputQuantity.getValue());
			returnValue.setTotalPrice(totalPrice.getValue());
			return returnValue;
		}
	}
	
	//Form untuk edit invoice
	private Label labelError;
	private TextField inputInvoiceNumber;
	private Label title;
	private DateField inputDueDate;
	private DateField inputInvoiceDate;
	private TextField inputAmountPaid;
	
	private FormLayout invoiceFormLayout;
	

	@Override
	public void generateInvoiceView(PurchaseOrder data) {
		viewInvoice.removeAllComponents();
		if(invoiceFormLayout==null){
			inputInvoiceNumber =new TextField("Nomor Faktur"){
				{
					setImmediate(true);
					addValueChangeListener(new ValueChangeListener() {

						@Override
						public void valueChange(
								com.vaadin.data.Property.ValueChangeEvent event) {
							hideError();
						}
					});
				}
			};
			title=new Label("<h3>Faktur untuk "+data.getPurchaseOrderName()+"</h3>", ContentMode.HTML);
			inputInvoiceDate=new DateField("Tanggal Faktur"){
				{
					setImmediate(true);
					setValue(new Date());
					addValueChangeListener(new ValueChangeListener() {

						@Override
						public void valueChange(
								com.vaadin.data.Property.ValueChangeEvent event) {
							hideError();
						}
					});
				}
			};
			inputDueDate=new DateField("Jatuh Tempo"){
				{
					setImmediate(true);
					setValue(new Date());
					addValueChangeListener(new ValueChangeListener() {

						@Override
						public void valueChange(
								com.vaadin.data.Property.ValueChangeEvent event) {
							hideError();
						}
					});
				}
			};
			inputAmountPaid=new TextField("Jumlah Dibayar"){
				{
					setImmediate(true);
					addValueChangeListener(new ValueChangeListener() {

						@Override
						public void valueChange(
								com.vaadin.data.Property.ValueChangeEvent event) {
							hideError();
						}
					});
				}
			};
			labelError=new Label(){
				{				
					setVisible(false);
					addStyleName("form-error");
					setContentMode(ContentMode.HTML);
					addValueChangeListener(new ValueChangeListener() {

						@Override
						public void valueChange(
								com.vaadin.data.Property.ValueChangeEvent event) {
							hideError();
						}
					});
					
				}
			};
			invoiceFormLayout =new FormLayout();
			invoiceFormLayout.addComponents(title, inputInvoiceNumber, inputInvoiceDate,
					inputDueDate, inputAmountPaid);
		}else{
			resetForm();
			title.setCaption("<h3>Faktur untuk "+data.getPurchaseOrderName()+"</h3>");
		}
		viewInvoice.addComponent(invoiceFormLayout);
		
		for(PurchaseOrderItem item:data.getPurchaseOrderItem()){
			InvoiceItemForm form=new InvoiceItemForm(function, item, listener, new ValueChangeListener() {
				
				@Override
				public void valueChange(ValueChangeEvent event) {
					hideError();
				}
			});
			viewInvoice.addComponent(form);
		}
	}
/*
 * 		private String invoiceNumber;
		private Date dueDate;
		private Date invoiceDate;
		private String amountPaid;
		private List<InvoiceItem> invoiceItems;
		
		private String batch="";
		private String discount="0";
		private String price="0";
		private String quantity="0";
		private boolean isPPN=false;
		private String totalPrice="0";
		private PurchaseOrderItem purchaseOrderItem;

(non-Javadoc)
 * @see com.binar.core.procurement.invoice.newInvoice.NewInvoiceView#getEditedInvoice()
 */
	@Override
	public FormData getEditedInvoice() {
		FormData data=new FormData();
		data.setAmountPaid(inputAmountPaid.getValue());
		data.setDueDate(inputDueDate.getValue());
		data.setInvoiceDate(inputInvoiceDate.getValue());
		data.setInvoiceNumber(inputInvoiceNumber.getValue());
		
		List<InvoiceItem> items=new ArrayList<NewInvoiceView.InvoiceItem>();
		while(invoiceFormLayout.iterator().hasNext()){
			Component component=invoiceFormLayout.iterator().next();
			if(component instanceof InvoiceItemForm){
				InvoiceItemForm invoiceForm = (InvoiceItemForm)component;
				items.add(invoiceForm.getData());
			}
		}
		System.out.println("Ukuran item" +items.size());
		data.setInvoiceItems(items);
		return data;
	}

	@Override
	public void resetForm() {
		viewInvoice.removeAllComponents();		
		inputInvoiceNumber.setValue("");
		inputDueDate.setValue(new Date());
		inputInvoiceDate.setValue(new Date());
		inputAmountPaid.setValue("");
		
	}
	public void showError(String content){
		labelError.setCaption(content);
		labelError.setVisible(true);
	}
	public void hideError(){
		labelError.setCaption("");
		labelError.setVisible(false);
	}
	@Override
	public void setFormView(FormViewEnum formView) {
		switch (formView) {
		//tampilan edit invoice
		case EDIT_VIEW: viewInvoice.setVisible(true);
						formSelectPurchaseOrder.setVisible(false);
						buttonNext.setVisible(false);
						buttonBack.setVisible(true);
						buttonCreate.setVisible(true);
			
			break;
		//tampilan milih purchaseorder
		case  INPUT_VIEW:viewInvoice.setVisible(false);
						 formSelectPurchaseOrder.setVisible(true);
						 buttonNext.setVisible(true);
					 	 buttonBack.setVisible(false);
						 buttonCreate.setVisible(false);
		default:
			break;
		}		
	}
	public int getPurchaseOrderSelect(){
		return (Integer)comboPurchaseOrder.getValue();
	}
	public Date getRangeStart(){
		return inputRangeStart.getValue();
	}
	public Date getRangeEnd(){
		return inputRangeEnd.getValue();
	}
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonBack){
			listener.buttonClick("buttonBack");
		}else if(event.getButton()==buttonCancel){
			listener.buttonClick("buttonCancel");
		}else if(event.getButton()==buttonCreate){
			listener.buttonClick("buttonCreate");
		}else if(event.getButton()==buttonNext){
			listener.buttonClick("buttonCreate");
		}
	}
	@Override
	public void valueChange(ValueChangeEvent event) {
		if(event.getProperty()==inputRangeEnd || 
				event.getProperty()==inputRangeStart){
			listener.periodChange();
		}
		
	}
	
}
