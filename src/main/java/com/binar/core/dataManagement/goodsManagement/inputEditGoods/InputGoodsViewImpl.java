package com.binar.core.dataManagement.goodsManagement.inputEditGoods;

import java.util.List;
import java.util.Map;

import com.binar.entity.Goods;
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
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class InputGoodsViewImpl extends FormLayout implements 
			InputGoodsView, ClickListener, TextChangeListener, ValueChangeListener {

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
	GeneralFunction function;
	GetSetting setting;
	TextManipulator text;
	
	//inisialisasi Form
	TextField inputId;
	TextField inputName;
	ComboBox inputInsurance;
	TextArea inputDescription;
	ComboBox inputType;
	ComboBox inputUnit;
	ComboBox inputPackage;
	ComboBox inputCategory;
	TextField inputMinimalStock;
	TextField inputHET;
	ComboBox inputImportant;
	TextArea inputInformation;
	TextField inputInitialStock;

	Label labelGeneralError;
	Label labelErrorId;
	Label labelErrorMinimalStock;
	Label labelErrorHET;
	Label labelErrorInitialStock;
	Button buttonSubmit;
	Button buttonSaveEdit;
	Button buttonReset;
	Button buttonCancel;
	
	InputGoodsListener listener;
	
	public InputGoodsViewImpl(GeneralFunction function){
		this.function=function;
		this.setting=function.getSetting();
		this.text=function.getTextManipulator();
	}
	@Override
	public void init() {
		inputId= new TextField("Kode Barang");
				inputId.setDescription("Kode barang harus unik");
				inputId.addValueChangeListener(this);
				inputId.setImmediate(true);
		inputName=new TextField("Nama Barang");
				inputName.setDescription("Nama dari barang");
				inputName.addValueChangeListener(this);
				inputName.setImmediate(true);
		inputInsurance= new ComboBox("Asuransi");
						inputInsurance.setDescription("Asuransi yang dipakai untuk barang ini");
		inputDescription =new TextArea("Deskripsi Barang");
		inputType = new ComboBox("Tipe Barang");
		inputUnit = new ComboBox("Satuan Barang");
		
		inputPackage= new ComboBox("Kemasan Barang");
		inputCategory =new ComboBox("Kategori Barang");
		inputMinimalStock =new TextField("Stok Minimal");
						   inputMinimalStock.setDescription("Minimal stok untuk barang ini");
						   inputMinimalStock.addValueChangeListener(this);
						   inputMinimalStock.setImmediate(true);
		inputHET = new TextField("Harga Eceran Tertinggi");
				   inputHET.addValueChangeListener(this);
				   inputMinimalStock.setImmediate(true);
		inputImportant=new ComboBox("Barang Penting?");
		inputInformation =new TextArea("Keterangan");
						inputInformation.setMaxLength(200);
		inputInitialStock =new TextField("Stok Awal");
						  inputInitialStock.setDescription("Stok awal saat data barang di input");
						  inputInitialStock.setImmediate(true);
						  inputInitialStock.addValueChangeListener(this);
						  inputInitialStock.setValue("0");
		buttonCancel =new Button("Batalkan");
					  buttonCancel.addClickListener(this);
		buttonReset = new Button("Reset");
					  buttonReset.addClickListener(this);
		buttonSaveEdit =new Button("Simpan Perubahan");
					  buttonSaveEdit.addClickListener(this);
		buttonSubmit=new Button("Simpan");
					 buttonSubmit.addClickListener(this);
					 
		labelErrorHET = new Label(){
			{
				setVisible(false);
				addStyleName("form-error");
				setContentMode(ContentMode.HTML);
			}
		};
		labelErrorId= new Label(){
			{
				setVisible(false);
				addStyleName("form-error");
				setContentMode(ContentMode.HTML);

			}
		};
		labelErrorMinimalStock =new Label(){
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
		labelErrorInitialStock=new Label(){
			{
				setVisible(false);
				addStyleName("form-error");
				setContentMode(ContentMode.HTML);
				
			}
		};
	}
	@Override
	public void construct() {
		this.addComponent(inputId);
		this.addComponent(labelErrorId);
		this.addComponent(inputInsurance);
		this.addComponent(inputDescription);
		this.addComponent(inputType);
		this.addComponent(inputUnit);
		this.addComponent(inputPackage);
		this.addComponent(inputCategory);
		this.addComponent(inputMinimalStock);
		this.addComponent(labelErrorMinimalStock);
		this.addComponent(inputHET);
		this.addComponent(labelErrorHET);
		this.addComponent(inputImportant);
		this.addComponent(inputInformation);
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
	public void setFormData(Goods data) {
		inputId.setValue(data.getIdGoods());;
		inputName.setValue(data.getName());;
		inputInsurance.setValue(data.getInsurance().getIdInsurance());;
		inputDescription.setValue(data.getDescription());;
		inputType.setValue(data.getType().toString());;
		inputUnit.setValue(data.getUnit());
		inputPackage.setValue(data.getGoodsPackage());;
		inputCategory.setValue(data.getCategory());;
		inputMinimalStock.setValue(data.getMinimumStock()+"");
		inputHET.setValue(text.doubleToRupiah(data.getHet()));
		inputImportant.setValue((Boolean)data.isImportant());
		inputInformation.setValue(data.getInformation());
		inputInitialStock.setValue(data.getCurrentStock()+"");
	}

	@Override
	public FormData getFormData() {
		FormData returnValue=new FormData(function);
		returnValue.setCategory((EnumGoodsCategory)inputCategory.getValue());
		returnValue.setDescription((String)inputDescription.getValue());
		returnValue.setGoodsPackage((String)inputPackage.getValue());
		returnValue.setHet((String)inputHET.getValue());
		returnValue.setId((String)inputId.getValue());
		returnValue.setImportant((Boolean)inputImportant.getValue());
		returnValue.setInformation((String)inputInformation.getValue());
		returnValue.setInsurance((String)inputInsurance.getValue());
		returnValue.setMinimalStock((String)inputMinimalStock.getValue());
		returnValue.setName((String)inputName.getValue());
		returnValue.setType((EnumGoodsType)inputType.getValue());
		returnValue.setUnit((String)inputUnit.getValue());
		returnValue.setInitialStock((String)inputInitialStock.getValue());
		return returnValue;
	}
	
	//untuk mengaktifkan dan menonaktifkan mode ubah
	@Override
	public void setEditMode(boolean editMode) {
		if(editMode){
			buttonSaveEdit.setVisible(true);
			buttonSubmit.setVisible(false);
			inputId.setEnabled(false);
		}else{
			buttonSaveEdit.setVisible(false);
			buttonSaveEdit.setVisible(true);
			inputId.setEnabled(true);
			
		}
		
		if(listener.isCanEditInitialStock()){
			inputInitialStock.setVisible(false);
		}else{
			inputInitialStock.setVisible(true);
		}

	}

	@Override
	public void resetForm() {
		inputCategory.setValue(inputCategory.getItemIds().toArray()[0]);
		inputDescription.setValue("");
		inputHET.setValue("0");
		inputId.setValue("");
		inputImportant.setValue(false);
		inputInformation.setValue("");
		inputInsurance.setValue(inputInsurance.getItemIds().toArray()[0]);
		inputMinimalStock.setValue("0");
		inputName.setValue("");
		inputPackage.setValue(inputPackage.getItemIds().toArray()[0]);
		inputType.setValue(inputType.getItemIds().toArray()[0]);
		inputUnit.setValue(inputUnit.getItemIds().toArray()[0]);
		inputInitialStock.setValue("0");
	}

	@Override
	public void showError(ErrorLabel label, String content) {
		switch (label) {
		case ID				: labelErrorId.setValue(content);
					  		  labelErrorId.setVisible(true);
					  		  break;
		case GENERAL		: labelGeneralError.setValue(content);
					  		  labelGeneralError.setVisible(true);
					  		  break;
		case HET  			: labelErrorHET.setValue(content);
					  		  labelErrorHET.setVisible(true);
					  		  break;
		case MINIMUM_STOCK	: labelErrorMinimalStock.setValue(content);
							  labelErrorMinimalStock.setVisible(true);
							  break;
		case INITIAL_STOCK  : labelErrorInitialStock.setValue(content);
							  labelErrorInitialStock.setVisible(true);
							  break;
		default:
			break;
		}
	}

	@Override
	public void hideError(ErrorLabel label) {
		switch (label) {
		case GENERAL		:labelGeneralError.setVisible(false);break;
		case HET			:labelErrorHET.setVisible(false);break;
		case ID				:labelErrorId.setVisible(false);break;
		case MINIMUM_STOCK	:labelErrorMinimalStock.setVisible(false);break;
		case INITIAL_STOCK 	:labelErrorInitialStock.setVisible(false);break;
		default:
			break;
		}
	}

	@Override
	public void hideAllError() {
		labelGeneralError.setVisible(false);
		labelErrorHET.setVisible(false);
		labelErrorId.setVisible(false);
		labelErrorMinimalStock.setVisible(false);
		labelErrorInitialStock.setVisible(false);
	}
	@Override
	public void valueChange(ValueChangeEvent event) {
		if(event.getProperty()==inputHET){
			listener.realTimeValidator("inputHET");
		}else if(event.getProperty()==inputId){
			listener.realTimeValidator("inputID");
		}else if(event.getProperty()==inputMinimalStock){
			listener.realTimeValidator("inputMinimalStock");
		}else if(event.getProperty()==inputInitialStock){
			listener.realTimeValidator("inputInitialStock");
		}
	}
	@Override
	public void textChange(TextChangeEvent event) {
		
	}
	@Override
	public void setComboBoxData(ComboDataList list) {
		
		//masukan untuk kategori
		Map<EnumGoodsCategory, String>  categoryList=list.getInputCategoryList();
		
        for (Map.Entry<EnumGoodsCategory, String> entry : categoryList.entrySet()) {
        	inputCategory.addItem(entry.getKey());
        	inputCategory.setItemCaption(entry.getKey(), entry.getValue());
        }
        
        Map<String, String>  packageList=list.getInputPackageList();
		
        for (Map.Entry<String, String> entry : packageList.entrySet()) {
        	inputPackage.addItem(entry.getKey());
        	inputPackage.setItemCaption(entry.getKey(), entry.getValue());
        }
        
        Map<EnumGoodsType, String>  typeList=list.getInputTypeList();
		
        for (Map.Entry<EnumGoodsType, String> entry : typeList.entrySet()) {
        	inputType.addItem(entry.getKey());
        	inputType.setItemCaption(entry.getKey(), entry.getValue());
        }
        
        Map<String, String>  unitList=list.getInputUnitList();
		
        for (Map.Entry<String, String> entry : unitList.entrySet()) {
        	inputUnit.addItem(entry.getKey());
        	inputUnit.setItemCaption(entry.getKey(), entry.getValue());
        }
        Map<String, String>  insuranceList=list.getInsuranceList();
		
        for (Map.Entry<String, String> entry : insuranceList.entrySet()) {
        	inputInsurance.addItem(entry.getKey());
        	inputInsurance.setItemCaption(entry.getKey(), entry.getValue());
        }
        Map<Boolean, String>  importantList=list.getInputImportantList();
		
        for (Map.Entry<Boolean, String> entry : importantList.entrySet()) {
        	inputImportant.addItem(entry.getKey());
        	inputImportant.setItemCaption(entry.getKey(), entry.getValue());
        }
	}
	
	
}
