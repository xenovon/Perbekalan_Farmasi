package com.binar.core.inventoryManagement.receptionList.inputReception;

import java.util.Date;
import java.util.Map;

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
	private TextField inputGoodsSelect;
	private TextField inputGoodsQuantity;
	private DateField inputExpiredDate;
	private DateField inputReceptionDate;
	private TextArea information;
	
	private Label labelSatuan;
	private Button buttonReset;
	private Button buttonSubmit;
	private Button buttonCancel;
	private TextManipulator text;
	private Label labelErrorQuantity; //untuk error realtime
	private Label labelGeneralError; //error saat tombol submit ditekan
	
	private InputReceptionListener listener;
	GeneralFunction function;
	
	public InputReceptionViewImpl(GeneralFunction function){
		this.function=function;
	}
	
	public void init() {
		text = new TextManipulator();
		final String WIDTH="350px";
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
		
		inputGoodsSelect=new TextField("Nama barang"){
			{
				setWidth(function.FORM_WIDTH);
				setEnabled(false);
			}
		};
		
		inputInvoiceItemSelect=new ComboBox("Invoice Item"){
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
		
		inputExpiredDate = new DateField("Tanggal Penerimaan"){
			{
				setWidth(function.FORM_WIDTH);
				setImmediate(true);
			}
		};
		inputExpiredDate.addValueChangeListener(this);
		
		information = new TextArea("informasi");
		information.setMaxLength(function.MAX_TEXTAREA_LENGTH);

		labelSatuan =new Label("Satuan");
		
		buttonReset=new Button("Reset");
		buttonReset.addClickListener(this);
		
		buttonSubmit=new Button("Submit");
		buttonSubmit.addClickListener(this);
		buttonSubmit.addStyleName("primary");
		buttonCancel =new Button("Batal");
		buttonCancel.addClickListener(this);
		construct();		
	}

	private void construct() {
		setMargin(true);
		this.setSpacing(true);
		this.addComponent(inputReceptionDate);
		this.addComponent(inputInvoiceItemSelect);
		this.addComponent(inputGoodsSelect);
		this.addComponent(labelSatuan);
		this.addComponent(inputGoodsQuantity);
		this.addComponent(labelErrorQuantity);
		this.addComponent(inputExpiredDate);
		this.addComponent(information);
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
		this.listener=listener;
	}

	public void resetForm() {
		this.inputGoodsQuantity.setValue("");
		this.information.setValue("");		
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		if(event.getProperty()==inputGoodsQuantity){
			listener.realTimeValidator("inputGoodsQuantity");
			
		}else if(event.getProperty()==inputInvoiceItemSelect){
			listener.realTimeValidator("inputInvoiceItemSelect");			
		}
		
		else if(event.getProperty()==inputReceptionDate){
			listener.realTimeValidator("inputConsumptionDate");			
		}
		
		else if(event.getProperty()==inputExpiredDate){
			listener.realTimeValidator("inputExpiredDate");			
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
		buttonSubmit.setCaption("Submit");
		resetForm();
	}

	public void setDataEdit(GoodsReception data){
		
		inputGoodsQuantity.setValue(data.getQuantityReceived()+"");
		setGoodsName(data.getInvoiceItem().getPurchaseOrderItem().getSupplierGoods().getGoods().getIdGoods());
		inputReceptionDate.setValue(data.getDate());
		inputExpiredDate.setValue(data.getExpiredDate());
		information.setValue(data.getInformation());
		
		buttonSubmit.setCaption("Simpan Perubahan");
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
	public void hideError(ErrorLabel label) {
		switch (label) {
		case GENERAL:labelGeneralError.setVisible(false);break;
		case QUANTITY:labelErrorQuantity.setVisible(false);break;
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
		if(isEdit){
			inputReceptionDate.setEnabled(false);
			inputInvoiceItemSelect.setEnabled(false);
			inputGoodsSelect.setEnabled(false);
		}else{
			inputReceptionDate.setEnabled(true);
			inputInvoiceItemSelect.setEnabled(true);
			inputGoodsSelect.setEnabled(false);
		}	
	}

	@Override
	public void setUnit(String text) {
		labelSatuan.setValue("Satuan : "+text);
	}
	

	@Override
	public void setGoodsName(String text) {
		inputGoodsSelect.setValue(text);
	}

	@Override
	public void setSelectInvoiceItemsData(Map<String, String> data) {
		for (Map.Entry<String, String> entry : data.entrySet()) {
        	inputInvoiceItemSelect.addItem(entry.getKey());
        	inputInvoiceItemSelect.setItemCaption(entry.getKey(), entry.getValue());
        }		
	}

	protected TextField getInputGoodsSelect() {		
		return inputGoodsSelect;
	}

	protected void setInputGoodsSelect(TextField inputGoodsSelect) {
		this.inputGoodsSelect = inputGoodsSelect;
	}

	protected TextField getInputGoodsQuantity() {
		return inputGoodsQuantity;
	}

	protected void setInputGoodsQuantity(TextField inputGoodsQuantity) {
		this.inputGoodsQuantity = inputGoodsQuantity;
	}

	public ComboBox getInputInvoiceItemSelect() {
		return inputInvoiceItemSelect;
	}

	public void setInputInvoiceItemSelect(ComboBox inputInvoiceItemSelect) {
		this.inputInvoiceItemSelect = inputInvoiceItemSelect;
	}

	public DateField getInputExpiredDate() {
		return inputExpiredDate;
	}

	public void setInputExpiredDate(DateField inputExpiredDate) {
		this.inputExpiredDate = inputExpiredDate;
	}

	public DateField getInputReceptionDate() {
		return inputReceptionDate;
	}

	public void setInputReceptionDate(DateField inputReceptionDate) {
		this.inputReceptionDate = inputReceptionDate;
	}

	protected TextArea getInformation() {
		return information;
	}

	protected void setInformation(TextArea information) {
		this.information = information;
	}

}
