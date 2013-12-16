package com.binar.core.dataManagement.supplierManagement.inputEditSupplier;

import java.util.List;
import java.util.Map;

import com.binar.entity.Goods;
import com.binar.entity.Supplier;
import com.binar.entity.enumeration.EnumGoodsCategory;
import com.binar.entity.enumeration.EnumGoodsType;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.binar.generalFunction.TextManipulator;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class InputSupplierViewImpl extends VerticalLayout implements 
			InputSupplierView, ClickListener, ValueChangeListener {

	/*
	 * Form yang dibutuhkan
	 * -Nama
	 * -Singkatan
	 * Deskripsi
	 * Alamat
	 * Nomor telepon
	 * Email
	 * FAX
	 */
	private GeneralFunction function;
	private GetSetting setting;
	private TextManipulator text;
	
	//inisialisasi Form
	private TextField inputAbbr;
	private TextField inputName;
	private TextArea inputDescription;
	private TextArea inputAddress;
	private TextArea inputPhoneNumber;
	private TextField inputEmail;
	private TextArea inputFax;
	private TextField inputCity;

	private Label labelGeneralError;
	private Label labelErrorEmail;
	private Label labelErrorAbbr;

	private Button buttonSubmit;
	private Button buttonSaveEdit;
	private Button buttonReset;
	private Button buttonCancel;
	
	private InputSupplierListener listener;
	private boolean editMode;
	
	public InputSupplierViewImpl(GeneralFunction function){
		this.function=function;
		this.setting=function.getSetting();
		this.text=function.getTextManipulator();
	}
	@Override
	public void init() {
		inputAbbr= new TextField("Singkatan Distributor");
				inputAbbr.addValueChangeListener(this);
				inputAbbr.setImmediate(true);
				inputAbbr.setWidth(function.FORM_WIDTH);
		inputName=new TextField("Nama Distributor");
				inputName.addValueChangeListener(this);
				inputName.setImmediate(true);
				inputName.setWidth(function.FORM_WIDTH);
		inputDescription =new TextArea("Deskripsi Distributor");
						  inputDescription.setWidth(function.FORM_WIDTH);

		inputAddress =new TextArea("Alamat");
						inputAddress.setMaxLength(200);
						inputAddress.setWidth(function.FORM_WIDTH);
		inputPhoneNumber =new TextArea("Nomor Telepon");
						inputPhoneNumber.setMaxLength(200);
						inputPhoneNumber.setWidth(function.FORM_WIDTH);
						
		inputEmail =new TextField("Email");
						  inputEmail.setImmediate(true);
						  inputEmail.addValueChangeListener(this);
						  inputEmail.setWidth(function.FORM_WIDTH);
		inputFax =new TextArea("Faksimile");
						inputFax.setImmediate(true);
						inputFax.addValueChangeListener(this);
						inputFax.setWidth(function.FORM_WIDTH);	
		inputCity =new TextField("Kota");
						inputCity.setImmediate(true);
						inputCity.addValueChangeListener(this);
						inputCity.setWidth(function.FORM_WIDTH);	
						
		buttonCancel =new Button("Batalkan");
					  buttonCancel.addClickListener(this);
		buttonReset = new Button("Reset");
					  buttonReset.addClickListener(this);
		buttonSaveEdit =new Button("Simpan Perubahan");
					  buttonSaveEdit.addClickListener(this);
		buttonSubmit=new Button("Simpan");
					 buttonSubmit.addClickListener(this);
					 
		labelErrorAbbr = new Label(){
			{
				setVisible(false);
				addStyleName("form-error");
				setContentMode(ContentMode.HTML);
			}
		};
		labelErrorEmail= new Label(){
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
		construct();
	}
	@Override
	public void construct() {
		
		setMargin(true);
		this.setSpacing(true);
		
		FormLayout layout=new FormLayout();
		layout.setSpacing(true);
		layout.addComponent(inputAbbr);
		layout.addComponent(labelErrorAbbr);
		layout.addComponent(inputName);
		layout.addComponent(inputDescription);
		layout.addComponent(inputAddress);
		layout.addComponent(inputPhoneNumber);
		layout.addComponent(inputEmail);
		layout.addComponent(labelErrorEmail);
		layout.addComponent(inputFax);
		layout.addComponent(inputCity);
		layout.addComponent(labelGeneralError);
		
		this.addComponent(layout);
		this.addComponent(new GridLayout(4,1){
			{
				this.setMargin(true);
				this.setSpacing(true);
				this.addComponent(buttonSaveEdit, 0,0);
				this.addComponent(buttonSubmit, 1, 0);
				this.addComponent(buttonReset, 2,0);
				this.addComponent(buttonCancel,3 ,0);
				
			}
		});
		this.setEditMode(false);
		
	}


	
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getSource()==buttonSubmit){
			listener.buttonClick("buttonSubmit");
		}if(event.getSource()==buttonCancel){
			listener.buttonClick("buttonCancel");
		}if(event.getSource()==buttonSaveEdit){
			listener.buttonClick("buttonSaveEdit");
		}if(event.getSource()==buttonReset){
			listener.buttonClick("buttonReset");
		}
		
	}

	@Override
	public void setFormData(Supplier data) {
		inputAbbr.setValue(data.getSupplierAbbr());;
		inputName.setValue(data.getSupplierName());;
		inputDescription.setValue(data.getDescription());
		inputAddress.setValue(data.getSupplierAddress());
		inputEmail.setValue(data.getEmail());
		inputCity.setValue(data.getCity());
		inputFax.setValue(data.getFax());
		inputPhoneNumber.setValue(data.getPhoneNumber());
		inputAbbr.setData(data.getSupplierAbbr());  //mengeset nilai awal Abbr yang tidak bisa dirubah
		System.out.println(inputAbbr.getData()+" data abbr");
	}

	@Override
	public FormData getFormData() {
		FormData returnValue=new FormData(function, editMode);
			returnValue.setAbbr(inputAbbr.getValue());
			returnValue.setAbbrInitValue((String) inputAbbr.getData()); //mengeset nilai awal ABBR ketika edit, untuk mendeteksi perubahan
			returnValue.setName(inputName.getValue());
			returnValue.setDescription(inputDescription.getValue());
			returnValue.setEmail(inputEmail.getValue());
			returnValue.setFax(inputFax.getValue());
			returnValue.setPhoneNumber(inputPhoneNumber.getValue());
			returnValue.setAddress(inputAddress.getValue());
			returnValue.setCity(inputCity.getValue());
		return returnValue;
	}
	
	//untuk mengaktifkan dan menonaktifkan mode ubah
	@Override
	public void setEditMode(boolean editMode) {
		if(editMode){
			buttonSaveEdit.setVisible(true);
			buttonSubmit.setVisible(false);
		}else{
			buttonSaveEdit.setVisible(false);
			buttonSubmit.setVisible(true);
			
		}
		this.editMode=editMode;
		

	}

	@Override
	public void resetForm() {
		inputAbbr.setValue("");
		inputAddress.setValue("");
		inputDescription.setValue("");
		inputEmail.setValue("");
		inputFax.setValue("");
		inputName.setValue("");
		inputPhoneNumber.setValue("");
		inputCity.setValue("");
	}

	@Override
	public void showError(ErrorLabel label, String content) {
		switch (label) {
		case EMAIL			: labelErrorEmail.setValue(content);
					  		  labelErrorEmail.setVisible(true);
					  		  break;
		case GENERAL		: labelGeneralError.setValue(content);
					  		  labelGeneralError.setVisible(true);
					  		  break;
		case ABBR  			: labelErrorAbbr.setValue(content);
					  		  labelErrorAbbr.setVisible(true);
					  		  break;
		default:
			break;
		}
	}

	@Override
	public void hideError(ErrorLabel label) {
		switch (label) {
		case GENERAL		:labelGeneralError.setVisible(false);break;
		case ABBR			:labelErrorAbbr.setVisible(false);break;
		case EMAIL			:labelErrorEmail.setVisible(false);break;
		default:
			break;
		}
	}

	@Override
	public void hideAllError() {
		labelGeneralError.setVisible(false);
		labelErrorEmail.setVisible(false);
		labelErrorAbbr.setVisible(false);
	}
	@Override
	public void valueChange(ValueChangeEvent event) {
		if(event.getProperty()==inputEmail){
			listener.realTimeValidator("inputEmail");
		}else if(event.getProperty()==inputAbbr){
			listener.realTimeValidator("inputAbbr");
		}
	}
	@Override
	public void setListener(InputSupplierListener listener) {
		this.listener=listener;
	}
	


	
}
