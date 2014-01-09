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
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

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
	private GeneralFunction function;
	private GetSetting setting;
	private TextManipulator text;
	
	//inisialisasi Form
	private TextField inputId;
	private TextField inputName;
	private ComboBox inputInsurance;
	private TextArea inputDescription;
	private ComboBox inputType;
	private ComboBox inputUnit;
	private ComboBox inputPackage;
	private ComboBox inputCategory;
	private TextField inputMinimalStock;
	private TextField inputHET;
	private ComboBox inputImportant;
	private TextArea inputInformation;
	private TextField inputInitialStock;

	private Label labelGeneralError;
	private Label labelErrorId;
	private Label labelErrorMinimalStock;
	private Label labelErrorHET;
	private Label labelErrorInitialStock;
	private Button buttonSubmit;
	private Button buttonSaveEdit;
	private Button buttonReset;
	private Button buttonCancel;
	
	private InputGoodsListener listener;
	private boolean editMode;
	
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
				inputId.setWidth(function.FORM_WIDTH);
		inputName=new TextField("Nama Barang");
				inputName.setDescription("Nama dari barang");
				inputName.addValueChangeListener(this);
				inputName.setImmediate(true);
				inputName.setWidth(function.FORM_WIDTH);
		inputInsurance= new ComboBox("Asuransi");
						inputInsurance.setDescription("Asuransi yang dipakai untuk barang ini");
						inputInsurance.setWidth(function.FORM_WIDTH);
	
		inputDescription =new TextArea("Deskripsi Barang");
						  inputDescription.setWidth(function.FORM_WIDTH);
		
		inputType = new ComboBox("Tipe Barang");
					inputType.setWidth(function.FORM_WIDTH);
					inputType.setImmediate(true);
		inputUnit = new ComboBox("Satuan Barang");
					inputUnit.setWidth(function.FORM_WIDTH);
					inputUnit.setImmediate(true);
		inputPackage= new ComboBox("Kemasan Barang");
					inputPackage.setWidth(function.FORM_WIDTH);
					inputUnit.setImmediate(true);
		inputCategory = new ComboBox("Kategori Barang");
						inputCategory.setWidth(function.FORM_WIDTH);
						inputCategory.setImmediate(true);
		inputMinimalStock =new TextField("Stok Minimal");
						   inputMinimalStock.setDescription("Minimal stok untuk barang ini");
						   inputMinimalStock.addValueChangeListener(this);
						   inputMinimalStock.setImmediate(true);
						   inputMinimalStock.setWidth(function.FORM_WIDTH);
		inputHET = new TextField("Harga Eceran Tertinggi");
				   inputHET.addValueChangeListener(this);
				   inputHET.setImmediate(true);
				   inputHET.setWidth(function.FORM_WIDTH);
		inputImportant=new ComboBox("Barang Fast Moving?");
						inputImportant.setImmediate(true);
						inputImportant.setWidth(function.FORM_WIDTH);
						inputImportant.setNullSelectionAllowed(false);
		inputInformation =new TextArea("Keterangan");
						inputInformation.setMaxLength(200);
						inputInformation.setWidth(function.FORM_WIDTH);
		inputInitialStock =new TextField("Stok Awal");
						  inputInitialStock.setDescription("Stok awal saat data barang di input");
						  inputInitialStock.setImmediate(true);
						  inputInitialStock.addValueChangeListener(this);
						  inputInitialStock.setWidth(function.FORM_WIDTH);
						  
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
		construct();
	}
	@Override
	public void construct() {
		setMargin(true);
		this.setSpacing(true);
		this.addComponent(inputId);
		this.addComponent(labelErrorId);
		this.addComponent(inputName);
		this.addComponent(inputInsurance);
		this.addComponent(inputDescription);
		this.addComponent(inputType);
		this.addComponent(inputUnit);
		this.addComponent(inputPackage);
		this.addComponent(inputCategory);
		this.addComponent(inputMinimalStock);
		this.addComponent(labelErrorMinimalStock);
		this.addComponent(inputInitialStock);
		this.addComponent(labelErrorInitialStock);
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
		inputType.setValue(data.getType());;
		inputUnit.setValue(data.getUnit());
		inputPackage.setValue(data.getGoodsPackage());;
		inputCategory.setValue(data.getCategory());;
		inputMinimalStock.setValue(data.getMinimumStock()+"");
		inputHET.setValue(data.getHet()+"");
		inputImportant.setValue((Boolean)data.isImportant());
		inputInformation.setValue(data.getInformation());
		inputInitialStock.setValue(data.getCurrentStock()+"");
	}

	@Override
	public FormData getFormData() {
		FormData returnValue=new FormData(function, editMode);
			returnValue.setCategory((EnumGoodsCategory)inputCategory.getValue());
			returnValue.setDescription((String)inputDescription.getValue());
			returnValue.setGoodsPackage((String)inputPackage.getValue());
			returnValue.setHet((String)inputHET.getValue());
			returnValue.setId((String)inputId.getValue());
			if(inputImportant.getValue()==null){
				System.out.println("Input important value null");
			}
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
			if(listener.isCanEditInitialStock()){
				inputInitialStock.setEnabled(true);
			}else{
				inputInitialStock.setEnabled(false);
			}
		}else{
			buttonSaveEdit.setVisible(false);
			buttonSubmit.setVisible(true);
			inputId.setEnabled(true);
			
		}
		this.editMode=editMode;
		

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
			listener.realTimeValidator("inputId");
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
        	System.out.println("Good type= " +entry.getKey() +" "+entry.getValue());
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
        inputImportant.setValue(false);
	}
	@Override
	public void setListener(InputGoodsListener listener) {
		this.listener=listener;
	}
	


	
}
