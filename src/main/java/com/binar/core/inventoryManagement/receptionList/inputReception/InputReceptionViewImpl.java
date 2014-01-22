package com.binar.core.inventoryManagement.receptionList.inputReception;

import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;

import com.binar.core.inventoryManagement.consumptionList.inputConsumption.FormConsumption;
import com.binar.entity.GoodsConsumption;
import com.binar.entity.GoodsReception;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.TextManipulator;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;

public class InputReceptionViewImpl extends FormLayout implements InputReceptionView, 
Button.ClickListener, ValueChangeListener {
	
	private ComboBox inputInvoiceItemSelect;
	private ComboBox inputGoodsSelect;
	private TextField inputGoodsQuantity;
	private DateField inputExpiredDate;
	private DateField inputReceptionDate;
	private DateField inputInvoiceStartDate;
	private DateField inputInvoiceEndDate;
	
	private TextArea inputInformation;
	private Label labelChooseDate;
	
	private Label labelSatuan;
	private Button buttonReset;
	private Button buttonSubmit;
	private Button buttonCancel;
	private TextManipulator text;
	private Label labelErrorQuantity; //untuk error realtime
	private Label labelGeneralError; //error saat tombol submit ditekan
	
	private InputReceptionListener listener;
	GeneralFunction function;
	private boolean editMode;
	
	
	
	public InputReceptionViewImpl(GeneralFunction function){
		this.function=function;
	}
	
	public void init() {
		text = new TextManipulator();
		labelErrorQuantity=new Label(){
			{
				setVisible(false);
				addStyleName("form-error");
				setContentMode(ContentMode.HTML);
			}
		};
		
		labelGeneralError=new Label(){
			{
				setVisible(false);
				addStyleName("form-error");
				setContentMode(ContentMode.HTML);
			}
		};
		inputGoodsQuantity=new TextField("Jumlah Penerimaan"){
			{
				setWidth(function.FORM_WIDTH);
				setImmediate(true);
			}
		};
		inputGoodsQuantity.addValueChangeListener(this);
		
		inputGoodsSelect=new ComboBox("Pilih Barang"){
			{
				setWidth(function.FORM_WIDTH);
				setEnabled(false);
				setImmediate(true);
			}
		};
		inputGoodsSelect.addValueChangeListener(this);
		inputInvoiceItemSelect=new ComboBox("Pilih Faktur"){
			{
				setImmediate(true);
				setWidth(function.FORM_WIDTH);
			}
		};
		inputInvoiceItemSelect.addValueChangeListener(this);
		
		inputReceptionDate = new DateField("Tanggal Penerimaan"){
			{
				setWidth(function.FORM_WIDTH);
				setImmediate(true);
			}
		};
		inputReceptionDate.addValueChangeListener(this);
		
		inputExpiredDate = new DateField("Tanggal Kadaluarsa"){
			{
				setWidth(function.FORM_WIDTH);
				setImmediate(true);
			}
		};
		inputExpiredDate.addValueChangeListener(this);
		inputInvoiceStartDate = new DateField("Rentang Awal"){
			{
				setWidth(function.FORM_WIDTH);
				setImmediate(true);
				DateTime defaultValue=new DateTime().now();
				setValue(defaultValue.withDayOfMonth(defaultValue.dayOfMonth().getMinimumValue()).toDate());
			}
		};
		inputInvoiceStartDate.addValueChangeListener(this);
		inputInvoiceEndDate = new DateField("Rentang Akhir"){
			{
				setWidth(function.FORM_WIDTH);
				DateTime defaultValue=new DateTime().now();
				setValue(defaultValue.withDayOfMonth(defaultValue.dayOfMonth().getMaximumValue()).toDate());
				setImmediate(true);
			}
		};
		inputInvoiceEndDate.addValueChangeListener(this);
		
		inputInformation = new TextArea("Informasi");
		inputInformation.setMaxLength(function.MAX_TEXTAREA_LENGTH);
		inputInformation.setWidth(function.FORM_WIDTH);

		labelSatuan =new Label("Satuan");
		
		buttonReset=new Button("Reset");
		buttonReset.addClickListener(this);
		
		buttonSubmit=new Button("Simpan");
		buttonSubmit.addClickListener(this);
		buttonSubmit.addStyleName("primary");
		buttonCancel =new Button("Batal");
		buttonCancel.addClickListener(this);
		
		labelChooseDate=new Label("<b>Pilih Faktur</b></br>Pilih Rentang Tanggal Faktur", ContentMode.HTML);
		construct();		
	}
	private void construct() {
		setMargin(true);
		this.setSpacing(true);
		this.addComponent(labelChooseDate);
		this.addComponent(inputInvoiceStartDate);
		this.addComponent(inputInvoiceEndDate);
		this.addComponent(inputInvoiceItemSelect);
		this.addComponent(inputGoodsSelect);
		this.addComponent(new Label("<b>Data Penerimaan Barang</b>", ContentMode.HTML));
		this.addComponent(inputReceptionDate);
		this.addComponent(inputGoodsQuantity);
		this.addComponent(labelSatuan);
		this.addComponent(labelErrorQuantity);
		this.addComponent(inputExpiredDate);
		this.addComponent(inputInformation);
		this.addComponent(labelGeneralError);
		this.addComponent(new GridLayout(3, 1){
			{
				addComponent(buttonSubmit, 0,0);
				addComponent(buttonReset, 1, 0);
				addComponent(buttonCancel, 2, 0);
				this.setMargin(true);
				this.setSpacing(true);
			}
		});		
	}
	
	public void setSelectedMonth(Date selectedDate) {
		inputReceptionDate.setValue(selectedDate);
	}

	public void addListener(InputReceptionPresenter inputReceptionPresenter) {
		this.listener=inputReceptionPresenter;
	}
	public void resetForm() {
		this.inputGoodsQuantity.setValue("");
		this.inputInformation.setValue("");
		
		inputExpiredDate.setValue(DateTime.now().withYear(DateTime.now().getYear()+4).toDate());
		inputExpiredDate.setValue(DateTime.now().toDate());
		DateTime defaultValue=new DateTime().now();
		inputInvoiceStartDate.setValue(defaultValue.withDayOfMonth(defaultValue.dayOfMonth().getMinimumValue()).toDate());
		inputInvoiceEndDate.setValue(defaultValue.withDayOfMonth(defaultValue.dayOfMonth().getMaximumValue()).toDate());
		
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		System.err.println("Value change event di penerimaan di [pagnggil");
		if(event.getProperty()==inputGoodsSelect){
			listener.realTimeValidator("inputGoodsSelect");
			
		}else if(event.getProperty()==inputInvoiceItemSelect){
			listener.realTimeValidator("inputInvoiceSelect");			
		}
		
		else if(event.getProperty()==inputInvoiceEndDate){
			listener.realTimeValidator("inputInvoiceEndDate");					
		}
		
		else if(event.getProperty()==inputInvoiceStartDate){
			listener.realTimeValidator("inputInvoiceStartDate");			
		}else if(event.getProperty()==inputGoodsQuantity){
			listener.realTimeValidator("inputGoodsQuantity");
		}
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getSource()==buttonReset){
			listener.buttonClick("reset");
		}else if(event.getSource()==buttonSubmit){
			listener.buttonClick("submit");
		}else if(event.getSource()==buttonCancel){
			listener.buttonClick("cancel");
		}		
	}
	
	public void setDataSubmit(){
		buttonSubmit.setCaption("Simpan");
		resetForm();
	}
	public void setDataEdit(GoodsReception data, Map<Integer, String> goodsData){
		
		setSelectGoodsData(goodsData);
		inputGoodsQuantity.setValue(data.getQuantityReceived()+"");
		inputGoodsSelect.setValue(data.getInvoiceItem().getIdInvoiceItem());
		inputReceptionDate.setValue(data.getDate());
		inputExpiredDate.setValue(data.getExpiredDate());
		inputInformation.setValue(data.getInformation());
		setUnit(data.getInvoiceItem().getPurchaseOrderItem().getSupplierGoods().getGoods().getUnit());
				
	}

	@Override
	public void showError(ErrorLabel label, String content) {
		switch (label) {
		case GENERAL:labelGeneralError.setVisible(true);
					 labelGeneralError.setValue(content);	
					 break;
		case QUANTITY:labelErrorQuantity.setVisible(true);
					  labelErrorQuantity.setValue(content);
					  break;
		default:
			break;
		}		
	}

	@Override
	public void hideAllError() {
		labelErrorQuantity.setVisible(false);
		labelGeneralError.setVisible(false);
	}

	@Override
	public void setInputEditView(boolean isEdit) {
		this.editMode = isEdit;
		if(editMode){
			inputInvoiceEndDate.setVisible(false);
			inputGoodsSelect.setEnabled(false);
			inputInvoiceStartDate.setVisible(false);
			inputInvoiceItemSelect.setVisible(false);
			labelChooseDate.setVisible(false);
			buttonSubmit.setCaption("Simpan Perubahan");
			buttonReset.setVisible(false);

		}else{
			inputInvoiceEndDate.setVisible(true);
			inputGoodsSelect.setEnabled(true);
			inputInvoiceStartDate.setVisible(true);
			inputInvoiceItemSelect.setVisible(true);
			labelChooseDate.setVisible(true);
			buttonReset.setVisible(false);
			buttonSubmit.setCaption("Simpan");			
		}
	}
	@Override
	public void setUnit(String text) {
		labelSatuan.setValue("Satuan : "+text);
		System.out.println(text);
	}
	

	@Override
	public void setSelectInvoiceItemsData(Map<Integer, String> data) {
		for (Map.Entry<Integer, String> entry : data.entrySet()) {
        	inputInvoiceItemSelect.addItem(entry.getKey());
        	inputInvoiceItemSelect.setItemCaption(entry.getKey(), entry.getValue());
        }		
	}

	@Override
	public void setSelectGoodsData(Map<Integer, String> data) {
        for (Map.Entry<Integer, String> entry : data.entrySet()) {
        	inputGoodsSelect.addItem(entry.getKey());
        	inputGoodsSelect.setItemCaption(entry.getKey(), entry.getValue());
        }
	}
	public Date getDateRangeStart(){
		return inputInvoiceStartDate.getValue();
	}
	public Date getDateDateRangeEnd(){
		return inputInvoiceEndDate.getValue();
	}
	public int getInvoiceSelect(){
		try {
			return (Integer) inputInvoiceItemSelect.getValue();
		} catch (Exception e) {
			return 0;
		}
	}
	public int getGoodsSelect(){
		try {
			return (Integer) inputGoodsSelect.getValue();
		} catch (Exception e) {
			return 0;
		}
	}
	@Override
	public FormReception getFormData() {
		FormReception data=new FormReception(function);
		data.setEditMode(this.editMode);
		data.setExpiredDate(inputExpiredDate.getValue());
		data.setInformation(inputInformation.getValue());
		data.setInvoiceItemId((Integer)inputGoodsSelect.getValue());
		data.setQuantity(inputGoodsQuantity.getValue());
		data.setReceptionDate(inputReceptionDate.getValue());
		System.out.println("input reception date"+inputReceptionDate.getValue().toString());
		return data;
	}

	public String getInputGoodsQuantity() {
		return inputGoodsQuantity.getValue();
	}


}
