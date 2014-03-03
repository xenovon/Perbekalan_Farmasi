package com.binar.core.setting.settingInsurance.inputEditInsurance;

import java.util.List;
import java.util.Map;

import com.binar.entity.Goods;
import com.binar.entity.Insurance;
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
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class InputInsuranceViewImpl extends FormLayout implements 
			InputInsuranceView, ClickListener {

	/*
	 * Form yang dibutuhkan
	 * -id
	 * -nama
	 * -Asuransi
	 * -deskripsi
	 * -tipe
	 * -satuan
	 * -keterangan
	 * -kemasan
	 * -kategori
	 * -stok minimal
	 * -penting
	 * -HET
	 */
	private GeneralFunction function;
	private GetSetting setting;
	private TextManipulator text;
	
	//inisialisasi Form
	private TextArea inputDescription;
	private TextField inputName;
	private CheckBox inputShow;
	private Label labelGeneralError;

	private Button buttonSubmit;
	private Button buttonSaveEdit;
	private Button buttonReset;
	private Button buttonCancel;
	
	private InputInsuranceListener listener;
	private boolean editMode;
	
	public InputInsuranceViewImpl(GeneralFunction function){
		this.function=function;
		this.setting=function.getSetting();
		this.text=function.getTextManipulator();
	}
	@Override
	public void init() {
		inputDescription= new TextArea("Deskripsi");
			inputDescription.setDescription("Deskripsi Dari Asuransi");
			inputDescription.setImmediate(true);
			inputDescription.setWidth(function.FORM_WIDTH);
		inputName=new TextField("Nama Asuransi");
				inputName.setDescription("Nama dari Asuransi");
				inputName.setImmediate(true);
				inputName.setWidth(function.FORM_WIDTH);
		inputShow=new CheckBox("Ditampilkan?");
			inputShow.setDescription("Apakah asuransi ditampilkan di pilihan input barang atau tidak");
			inputShow.setImmediate(true);
			inputShow.setWidth(function.FORM_WIDTH);
				
						  
		buttonCancel =new Button("Batalkan");
					  buttonCancel.addClickListener(this);
		buttonReset = new Button("Reset");
					  buttonReset.addClickListener(this);
		buttonSaveEdit =new Button("Simpan Perubahan");
					  buttonSaveEdit.addClickListener(this);
		buttonSubmit=new Button("Simpan");
					 buttonSubmit.addClickListener(this);
					 
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
		this.addComponent(inputName);
		this.addComponent(inputDescription);
		this.addComponent(labelGeneralError);
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
	public void setFormData(Insurance data) {
		inputName.setValue(data.getName());;
		inputDescription.setValue(data.getDescription());;
		
	}

	@Override
	public FormData getFormData() {
		FormData returnValue=new FormData(function);
		returnValue.setDescription(inputDescription.getValue());
		returnValue.setName(inputName.getValue());
		returnValue.setShow(inputShow.getValue());
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
		inputDescription.setValue("");
		inputName.setValue("");
		inputShow.setValue(false);
	}

	@Override
	public void showError(String content) {
		labelGeneralError.setValue(content);
		labelGeneralError.setVisible(true);
	}

	@Override
	public void hideError() {
		labelGeneralError.setVisible(false);
	}
	@Override
	public void setListener(InputInsuranceListener listener) {
		this.listener=listener;
	}


	
}
