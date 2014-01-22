package com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm;

import java.awt.peer.TextFieldPeer;
import java.util.Map;

import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class InputFormViewImpl extends FormLayout implements 
									   InputFormView, Button.ClickListener,
									   ValueChangeListener
									   {

	/*
	 * Form dibutuhkan
	 * -> Select Obat
	 * -> jumlah kebutuhan + tombol cek forecasting  forecasting
	 * -> produsen
	 * -> distributor
	 * -> Harga (input sendiri jika belum tersedia )
	 * -> 
	 * 
	 */
	
	private ComboBox inputGoodsSelect;
	private TextField inputGoodsQuantity;
	private Button buttonCheckForecast;
	private ComboBox inputManufacturer;
	private ComboBox inputSupplier;
	private TextField inputPrice;
	private TextArea inputInformation;
	//untuk label unit di samping jumlah kebutuhan, isi sesuai jenis barang
	private Label labelSatuan;
	private Button buttonReset;
	private Button buttonSubmit;
	private Button buttonCancel;
	
	private Label labelErrorQuantity; //untuk error realtime
 	private Label labelErrorPrice; //untuk error realtime
	private Label labelGeneralError; //error saat tombol submit ditekan
	
	private InputFormListener listener;

	GeneralFunction function;
	
	public InputFormViewImpl(GeneralFunction function){
		this.function=function;
	}
	
	public void init(){
		final String WIDTH="250px";
		//inisiasi
		labelErrorPrice=new Label(){
			{
				setVisible(false);
				addStyleName("form-error");
				setContentMode(ContentMode.HTML);
			}
		};
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
		inputGoodsQuantity=new TextField("Jumlah Kebutuhan"){
			{
				setWidth(function.FORM_WIDTH);
				setImmediate(true);
			}
		};
		// TODO tambahkan komponen label error
		inputGoodsQuantity.addValueChangeListener(this);
		inputGoodsSelect=new ComboBox("Nama Barang"){
			{
				setImmediate(true);
				setWidth(function.FORM_WIDTH);
			}
		};
		inputGoodsSelect.addValueChangeListener(this);

		inputManufacturer =new ComboBox("Produsen"){
			{
				setWidth(function.FORM_WIDTH);
				setImmediate(true);
			
			}
		};
		inputManufacturer.addValueChangeListener(this);
		inputSupplier =new ComboBox("Distributor"){
			{
				setImmediate(true);
				setWidth(function.FORM_WIDTH);
			}
		};
		inputSupplier.addValueChangeListener(this);
		inputPrice= new TextField("Harga Barang"){
			{
				setImmediate(true);
				setWidth(function.FORM_WIDTH);
			}
		};
		inputPrice.addValueChangeListener(this);
		
		buttonCheckForecast=new Button("Cek Peramalan");
		buttonCheckForecast.addClickListener(this);
		
		inputInformation =new TextArea("Keterangan"){
			{
				setImmediate(true);
				setWidth(function.FORM_WIDTH);
				setMaxLength(function.MAX_TEXTAREA_LENGTH);
			}
		};

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
	//set data yang mesti diedit
	public void setDataEdit(ReqPlanning data){
		
		inputGoodsQuantity.setValue(String.valueOf(data.getQuantity()));
		inputGoodsSelect.setValue(data.getSupplierGoods().getGoods().getIdGoods());
		inputManufacturer.setValue(String.valueOf(data.getSupplierGoods().getManufacturer().getIdManufacturer()));
		inputSupplier.setValue(String.valueOf(data.getSupplierGoods().getSupplier().getIdSupplier()));
		inputPrice.setValue(String.valueOf(data.getPriceEstimation()));
		inputInformation.setValue(data.getInformation());
		buttonSubmit.setCaption("Simpan Perubahan");
	}
	//restore dari posisi edit ke submit;
	public void setDataSubmit(){
		buttonSubmit.setCaption("Submit");
		resetForm();
	}
	
	/* untuk menkonstruksi tampilan */
	private void construct(){
		setMargin(true);
		this.setSpacing(true);

		this.addComponent(inputGoodsSelect);
		this.addComponent(inputGoodsQuantity);
		this.addComponent(labelErrorQuantity);
		this.addComponent(new GridLayout(2,1){
			{
				addComponent(buttonCheckForecast, 0, 0);
				addComponent(labelSatuan, 1,0);
				setSpacing(true);
			}
		});
		this.addComponent(inputSupplier);
		this.addComponent(inputManufacturer);
		this.addComponent(inputPrice);
		this.addComponent(labelErrorPrice);
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
	
	public void addListener(InputFormListener listener){
		this.listener=listener;
	}
	public void resetForm(){

		this.inputGoodsQuantity.setValue("");
		this.inputGoodsSelect.setValue("");
		this.inputInformation.setValue("");
		this.inputPrice.setValue("");
		this.inputManufacturer.setValue("");
		this.inputSupplier.setValue("");
	}
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getSource()==buttonCheckForecast){
			listener.buttonClick("forecast");
		}else if(event.getSource()==buttonReset){
			listener.buttonClick("reset");
		}else if(event.getSource()==buttonSubmit){
			listener.buttonClick("submit");
		}else if(event.getSource()==buttonCancel){
			listener.buttonClick("cancel");
		}
		
	}
	@Override
	public void setSelectGoodsData(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
        	inputGoodsSelect.addItem(entry.getKey());
        	inputGoodsSelect.setItemCaption(entry.getKey(), entry.getValue());
        }
		
	}
	@Override
	public void setSelectManufacturerData(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
        	inputManufacturer.addItem(entry.getKey());
        	inputManufacturer.setItemCaption(entry.getKey(), entry.getValue());
        }		
	}
	@Override
	public void setSelectSupplierData(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
        	inputSupplier.addItem(entry.getKey());
        	inputSupplier.setItemCaption(entry.getKey(), entry.getValue());
        }			
	}
	@Override
	public void valueChange(ValueChangeEvent event) {
		if(event.getProperty()==inputGoodsQuantity){
			listener.realTimeValidator("inputGoodsQuantity");
		}else if(event.getProperty()==inputGoodsSelect){
			listener.realTimeValidator("inputGoodsSelect");			
		}else if(event.getProperty()==inputPrice){
			listener.realTimeValidator("inputPrice");			
		}else if(event.getProperty()==inputManufacturer){
			listener.realTimeValidator("inputManufacturer");						
		}else if(event.getProperty()==inputSupplier){
			listener.realTimeValidator("inputSupplier");			
		}
	}
	
	public void showError(ErrorLabel label, String content){
		switch (label) {
		case GENERAL:labelGeneralError.setVisible(true);
					 labelGeneralError.setValue(content);	
					 break;
		case QUANTITY:labelErrorQuantity.setVisible(true);
					  labelErrorQuantity.setValue(content);
					  break;
		case SUPPLIER:labelErrorPrice.setVisible(true);
					  labelErrorPrice.setValue(content);
					  break;
		default:
			break;
		}
	}
	public void hideError(ErrorLabel label){
		switch (label) {
		case GENERAL:labelGeneralError.setVisible(false);break;
		case QUANTITY:labelErrorQuantity.setVisible(false);break;
		case SUPPLIER : labelErrorPrice.setVisible(false);break;
		default:
			break;
		}
	}
	public void hideAllError(){
		labelErrorPrice.setVisible(false);
		labelErrorQuantity.setVisible(false);
		labelGeneralError.setVisible(false);
	}
	@Override
	public void setUnit(String text) {
		labelSatuan.setValue("Satuan : "+text);
	}
	protected ComboBox getInputGoodsSelect() {
		return inputGoodsSelect;
	}

	protected void setInputGoodsSelect(ComboBox inputGoodsSelect) {
		this.inputGoodsSelect = inputGoodsSelect;
	}

	protected TextField getInputGoodsQuantity() {
		return inputGoodsQuantity;
	}

	protected void setInputGoodsQuantity(TextField inputGoodsQuantity) {
		this.inputGoodsQuantity = inputGoodsQuantity;
	}

	protected ComboBox getInputManufacturer() {
		return inputManufacturer;
	}

	protected void setInputManufacturer(ComboBox inputManufacturer) {
		this.inputManufacturer = inputManufacturer;
	}

	protected ComboBox getInputSupplier() {
		return inputSupplier;
	}

	protected void setInputSupplier(ComboBox inputSupplier) {
		this.inputSupplier = inputSupplier;
	}

	protected TextField getInputPrice() {
		return inputPrice;
	}

	protected void setInputPrice(TextField inputPrice) {
		this.inputPrice = inputPrice;
	}

	protected TextArea getInputInformation() {
		return inputInformation;
	}

	protected void setInputInformation(TextArea inputInformation) {
		this.inputInformation = inputInformation;
	}
	
	
	
	
	
}
