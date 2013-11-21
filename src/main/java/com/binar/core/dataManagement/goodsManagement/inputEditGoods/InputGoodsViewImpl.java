package com.binar.core.dataManagement.goodsManagement.inputEditGoods;

import java.util.List;

import com.binar.entity.Goods;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
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

	Label labelGeneralError;
	Label labelErrorId;
	Label labelErrorMinimalStock;
	Label labelErrorHET;
	
	Button buttonSubmit;
	Button buttonSaveEdit;
	Button buttonReset;
	Button buttonCancel;
	
	InputGoodsListener listener;
	
	public InputGoodsViewImpl(GeneralFunction function){
		this.function=function;
		this.setting=function.getSetting();
	}
	@Override
	public void init() {
		inputId= new TextField("Kode Barang");
				inputId.setDescription("Kode barang harus unik");
				inputId.addTextChangeListener(this);
		inputName=new TextField("Nama Barang");
				inputName.setDescription("Nama dari barang");
				inputName.addValueChangeListener(this);
			
		inputInsurance= new ComboBox("Asuransi");
						inputInsurance.setDescription("Asuransi yang dipakai untuk barang ini");
		inputDescription =new TextArea("Deskripsi Barang");
		inputType = new ComboBox("Tipe Barang");
		inputUnit = new ComboBox("Satuan Barang");
		inputPackage= new ComboBox("Kemasan Barang");
		inputCategory =new ComboBox("Kategori Barang");
		inputMinimalStock =new TextField("Stok Minimal");
						   inputMinimalStock.setDescription("Minimal stok untuk barang ini");
						   
		inputHET = new TextField("Harga Eceran Tertinggi");
		inputImportant=new ComboBox("Barang Penting?");
		inputInformation =new TextArea("Keterangan");
						inputInformation.setMaxLength(200);
		
		buttonCancel =new Button("Batalkan");
					  buttonCancel.addClickListener(this);
		buttonReset = new Button("Reset");
					  buttonReset.addClickListener(this);
		buttonSaveEdit =new Button("Simpan Perubahan");
					  buttonSaveEdit.addClickListener(this);
		buttonSubmit=new Button("Simpan");
					 buttonSubmit.addClickListener(this);
	}

	@Override
	public void construct() {
		this.addComponent(c);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public FormData getFormData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEditMode(boolean editMode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetForm() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showError(ErrorLabel label, String content) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hideError(ErrorLabel label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hideAllError() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void textChange(TextChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setComboBoxData(ComboDataList list) {
		// TODO Auto-generated method stub
		
	}
	
	
}
