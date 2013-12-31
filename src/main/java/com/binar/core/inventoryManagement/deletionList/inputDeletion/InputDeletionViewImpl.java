package com.binar.core.inventoryManagement.deletionList.inputDeletion;

import java.util.Map;
import java.util.Date;

import com.binar.entity.DeletedGoods;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.TextManipulator;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;


import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.GridLayout;

public class InputDeletionViewImpl extends FormLayout implements InputDeletionView, 
Button.ClickListener, ValueChangeListener {

	private ComboBox inputGoodsSelect;
	private TextField inputGoodsQuantity;
	private DateField inputDeletionDate;
	private TextArea information;
	private Label labelSatuan;
	private Button buttonReset;
	private Button buttonSubmit;
	private Button buttonCancel;
	private TextManipulator text;
	private Label labelErrorQuantity; //untuk error realtime
	private Label labelGeneralError; //error saat tombol submit ditekan
	
	private InputDeletionPresenter listener;
	GeneralFunction function;

	public InputDeletionViewImpl(GeneralFunction function) {
		this.function=function;
	}
	
	@Override
	public void valueChange(ValueChangeEvent event) {
	if(event.getProperty()==inputGoodsQuantity){
		listener.realTimeValidator("inputGoodsQuantity");
		
	}else if(event.getProperty()==inputGoodsSelect){
		listener.realTimeValidator("inputGoodsSelect");			
		}
	
	else if(event.getProperty()==inputDeletionDate){
		listener.realTimeValidator("inputDeletionDate");
		}		
	}

	public void setSelectedMonth(Date selectedDate){
		inputDeletionDate.setValue(selectedDate);
	}
	
	@Override
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
		inputGoodsQuantity=new TextField("Jumlah"){
			{
				setWidth(function.FORM_WIDTH);
				setImmediate(true);
			}
		};
		inputGoodsQuantity.addValueChangeListener(this);
		
		inputGoodsSelect=new ComboBox("Nama Barang"){
			{
				setImmediate(true);
				setWidth(function.FORM_WIDTH);
			}
		};
		inputGoodsSelect.addValueChangeListener(this);
		
		inputDeletionDate = new DateField("Tanggal"){
			{
				setWidth(function.FORM_WIDTH);
				setImmediate(true);
			}
		};
		inputDeletionDate.addValueChangeListener(this);
		
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

		this.addComponent(inputDeletionDate);
		this.addComponent(inputGoodsSelect);
		this.addComponent(inputGoodsQuantity);
		this.addComponent(labelErrorQuantity);
		this.addComponent(labelSatuan);
		this.addComponent(labelGeneralError);
		this.addComponent(information);
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

	@Override
	public void resetForm() {
		this.inputGoodsQuantity.setValue("");
		this.inputGoodsSelect.setValue("");
		this.information.setValue("");
	}

	public void setDataSubmit(){
		buttonSubmit.setCaption("Submit");
		resetForm();
	}
	
	public void setDataEdit(DeletedGoods data){
		
		inputGoodsQuantity.setValue(data.getQuantity()+"");
		inputGoodsSelect.setValue(data.getSupplierGoods().getGoods().getIdGoods());
		inputDeletionDate.setValue(data.getDeletionDate());
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
		//	inputDeletionDate.setEnabled(false);
			inputGoodsSelect.setEnabled(false);
		}else{
		//	inputDeletionDate.setEnabled(true);
			inputGoodsSelect.setEnabled(true);
		}
	}
	
	@Override
	public void setUnit(String text) {
		labelSatuan.setValue("Satuan : "+text);
	}

	@Override
	public void setSelectGoodsData(Map<String, String> data) {
		for (Map.Entry<String, String> entry : data.entrySet()) {
        	inputGoodsSelect.addItem(entry.getKey());
        	inputGoodsSelect.setItemCaption(entry.getKey(), entry.getValue());
        }
	}

	@Override
	public void addListener(InputDeletionPresenter listener) {
		this.listener=listener;
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

	public ComboBox getInputGoodsSelect() {
		return inputGoodsSelect;
	}

	public void setInputGoodsSelect(ComboBox inputGoodsSelect) {
		this.inputGoodsSelect = inputGoodsSelect;
	}

	public TextField getInputGoodsQuantity() {
		return inputGoodsQuantity;
	}

	public void setInputGoodsQuantity(TextField inputGoodsQuantity) {
		this.inputGoodsQuantity = inputGoodsQuantity;
	}

	public DateField getInputDeletionDate() {
		return inputDeletionDate;
	}

	public void setInputDeletionDate(DateField inputDeletionDate) {
		this.inputDeletionDate = inputDeletionDate;
	}

	public TextArea getInformation() {
		return information;
	}

	public void setInformation(TextArea information) {
		this.information = information;
	}
}
