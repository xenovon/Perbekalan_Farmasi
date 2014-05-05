package com.binar.core.inventoryManagement.deletionList.inputDeletion;

import java.util.ArrayList;
import java.util.List;
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


import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.GridLayout;

public class InputDeletionViewImpl extends FormLayout implements InputDeletionView, 
Button.ClickListener, ValueChangeListener {

	private ComboBox inputGoodsSelect;
	private TextField inputGoodsQuantity;
	private TextField inputGoodsPrice;
	private DateField inputDeletionDate;
	private TextArea information;
	private Label labelSatuan;
	private Button buttonReset;
	private Button buttonSubmit;
	private Button buttonCancel;
	private Button buttonUpdate;
	
	private Button buttonNext;
	private Button buttonPrev;
	private Button buttonAdd;
	private Button buttonRemove;

	
	private TextManipulator text;
	private Label labelErrorQuantity; //untuk error realtime
	private Label labelGeneralError; //error saat tombol submit ditekan
	private Label labelErrorPrice;
	private Label labelPriceGuide;
	private int deletionId;
	private InputDeletionListener listener;
	GeneralFunction function;

	String periode="";
	private List<FormDeletion> formDataList;

	public InputDeletionViewImpl(GeneralFunction function) {
		this.function=function;
		this.text=function.getTextManipulator();
	
	}
	@Override
	public void valueChange(ValueChangeEvent event) {
	if(event.getProperty()==inputGoodsQuantity){
		listener.quantityChange();
		
	}else if(event.getProperty()==inputGoodsSelect){
		listener.goodsSelectChange();
	}
	}
	
	@Override
	public void init() {
		final String WIDTH="350px";
		labelErrorQuantity=new Label(){
			{
				setVisible(false);
				addStyleName("form-error");
				setContentMode(ContentMode.HTML);
			}
		};
		labelErrorPrice=new Label(){
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
		
		inputGoodsPrice=new TextField("Harga Barang");
		inputGoodsPrice.setImmediate(true);
		inputGoodsPrice.setWidth(function.FORM_WIDTH);
		
		inputDeletionDate = new DateField("Tanggal"){
			{
				setWidth(function.FORM_WIDTH);
				setImmediate(true);
			}
		};
		inputDeletionDate.addValueChangeListener(this);
		
		information = new TextArea("Informasi");
		information.setMaxLength(function.MAX_TEXTAREA_LENGTH);
		information.setWidth(function.FORM_WIDTH);
		labelPriceGuide =new Label("");
		labelPriceGuide.setContentMode(ContentMode.HTML);
		
		labelSatuan =new Label("Satuan");
		
		buttonReset=new Button("Reset");
		buttonReset.addClickListener(this);
		
		buttonSubmit=new Button("Simpan");
		buttonSubmit.addClickListener(this);
		buttonSubmit.addStyleName("primary");
		buttonCancel =new Button("Batal");
		buttonCancel.addClickListener(this);
		buttonUpdate =new Button("Simpan Perubahan");
		buttonUpdate.addClickListener(this);
		
		buttonNext =new Button(">>");
		buttonNext.addClickListener(this);
		buttonNext.setWidth("50px");
		buttonNext.setEnabled(false);
		buttonPrev =new Button("<<");
		buttonPrev.addClickListener(this);
		buttonPrev.setWidth("50px");
		buttonPrev.setEnabled(false);
		buttonPrev.setDescription("Sebelumnya");
		
		buttonAdd =new Button("Tambah");
		buttonAdd.addClickListener(this);
		buttonAdd.setWidth("130px");
		buttonAdd.setDescription("Tambah Input Baru");

		buttonRemove =new Button("");
		buttonRemove.setIcon(new ThemeResource("icons/image/icon-delete.png"));
		buttonRemove.addClickListener(this);
		buttonRemove.setDescription("Hapus Data");

		
		construct();
	}
	GridLayout layoutButtonNav;
	private void construct() {
		setMargin(true);
		this.setSpacing(true);
		formDataList=new ArrayList<FormDeletion>();

		this.addComponent(inputDeletionDate);
		this.addComponent(inputGoodsSelect);
		this.addComponent(inputGoodsQuantity);
		this.addComponent(labelErrorQuantity);
		this.addComponent(labelSatuan);
		this.addComponent(inputGoodsPrice);
		this.addComponent(labelPriceGuide);
		this.addComponent(labelErrorPrice);
		this.addComponent(information);
		this.addComponent(labelGeneralError);
		
		layoutButtonNav=new GridLayout(5, 1){
			{
				addComponent(buttonPrev, 0,0);
				addComponent(buttonAdd, 1, 0);
				addComponent(buttonNext, 2, 0);
				addComponent(new Label("--"), 3, 0);
				addComponent(buttonRemove, 4, 0);
				this.setMargin(true);
				this.setSpacing(true);
			}
		};
		this.addComponent(layoutButtonNav);
		//menambahkan layout ke daftar form

		
		this.addComponent(new GridLayout(4, 1){
			{
				addComponent(buttonSubmit, 0,0);
				addComponent(buttonUpdate, 1,0);
				addComponent(buttonReset, 2, 0);
				addComponent(buttonCancel, 3, 0);
				this.setMargin(true);
				this.setSpacing(true);
			}
		});
	}
	
	//-------------Kode tambahan untuk mengatur tampilan form  //
	private int currentPos=1;
	private int posCount=1;
	private final int DATA_LIMIT=10;
	
	public int getCurrentPos() {
		return currentPos;
	}
	public int getPosCount() {
		return posCount;
	}
	public List<FormDeletion> getFormDataList() {
		return formDataList;
	}
	//METHOD UTAMA
	public void addForm(){
		FormDeletion data=getFormData();
		buttonAddMan();
		if(validate(data)){
			resetForm();
			posCount=posCount+1;
			currentPos=posCount;
			formDataList.add(data);
			changeButtonText();
			buttonActivator();		
//			System.out.println(posCount +"posCount");
//			System.out.println(currentPos +"current");
//			System.out.println(data.toString());
		}
		
	}
	public void nextView(){
		FormDeletion newData=getFormData();
		if(validate(newData)){
			formDataList.set(currentPos-1, newData);
			currentPos=currentPos+1;
			//ambil data index ke x;
			//min 1 karena currentPost awal dari 1, tapi list dari 0
//			System.out.println(formDataList.get(currentPos-1).toString());
//			System.out.println(formDataList.toString());
			buttonActivator();		
			setPosition(currentPos);
		}
		changeButtonText();
	}
	public void prevView(){
		FormDeletion data;
		//terjadi ketika data belum dimasukan ke formDataList
		//
		data=getFormData();

		if(validate(data)){
			//jika baru nambah page tapi belum disimpan di formDataList
			if(posCount>formDataList.size()){
				formDataList.add(data);
			}else{
				formDataList.set(currentPos-1, data);				
			}
			currentPos=currentPos-1;
			buttonActivator();
			//ambil data index ke x;
			//min 1 karena currentPost awal dari 1, tapi list dari 0
//			System.out.println(formDataList.get(currentPos-1).toString());
//			System.out.println(formDataList.toString());
			
			setPosition(currentPos);

		}
		changeButtonText();
	}
	public void remove(){
		resetForm();
		buttonAddMan();
		if(posCount>1){
			posCount=posCount-1;
			if(formDataList.size()>posCount){
				formDataList.remove(currentPos-1);				
			}
			if(currentPos!=1){
				currentPos=currentPos-1;				
			}
			FormDeletion data=formDataList.get(currentPos-1);
			buttonActivator();
			changeButtonText();	
			setPosition(currentPos);
//			System.out.println(formDataList.toString());
		}
	}
	public void resetView(){
		currentPos=1;
		posCount=1;
		resetForm();
		formDataList=new ArrayList<FormDeletion>();
	}
	//-------------------------
	//METHOD PENDUKUNG
	private void buttonAddMan(){
		if(!(posCount<DATA_LIMIT)){
			buttonAdd.setEnabled(false);
			return;
		}else{
			buttonAdd.setEnabled(true);
		}

	}
	private void changeButtonText(){
		buttonAdd.setCaption("Tambah-" +currentPos+" ("+posCount+")");
	}
	private boolean validate(FormDeletion data){
		List<String> errors=data.validate();
		if(errors!=null){
			String textError="Silahkan koreksi Error berikut untuk melanjutkan : </br>";
			for(String error:errors){
				textError=textError+error+"</br>";
			}
			showError(ErrorLabel.GENERAL, textError);
			return false;
		}
		return true;
		

	}
	private void buttonActivator(){
		if(currentPos==1){
			buttonPrev.setEnabled(false);
		}else{
			buttonPrev.setEnabled(true);
		}
		
		if(currentPos==posCount){
			buttonNext.setEnabled(false);
		}else{
			buttonNext.setEnabled(true);
		}
		
		if(posCount>=DATA_LIMIT){
			buttonAdd.setEnabled(false);
		}else{
			buttonAdd.setEnabled(true);
		}
	}
	private void setPosition(int pos){
		if(!(pos>formDataList.size())){
			FormDeletion data=formDataList.get(pos-1);
			setData(data);
		}
	}
	private FormDeletion getFormData(FormDeletion form){
		form.setDeletionDate(inputDeletionDate.getValue());
		form.setInformation(information.getValue());
		form.setQuantity(inputGoodsQuantity.getValue());
		form.setIdGoods((String) inputGoodsSelect.getValue());
		form.setDeletionId(this.deletionId);
		form.setPrice(inputGoodsPrice.getValue());
		
		return form;
	}
	//MEngeset data dari FormData ke form
	private void setData(FormDeletion data){
		//untuk kebutuhan edit
		
		inputDeletionDate.setValue(data.getDeletionDate());
		information.setValue(data.getInformation());
		inputGoodsQuantity.setValue(data.getQuantity());
		inputGoodsSelect.setValue(data.getIdGoods());
		this.deletionId=data.getDeletionId();
		inputGoodsPrice.setValue(data.getPrice());
		
	}
	//-------------------------------Kode tambahan untuk mengatur tampilan form  //

	@Override
	public void resetForm() {
		this.inputGoodsQuantity.setValue("");
		this.inputGoodsSelect.setValue(null);
		this.information.setValue("");
		this.inputGoodsPrice.setValue("");
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
		case PRICE : labelErrorPrice.setVisible(true);
					 labelErrorPrice.setValue(content);
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
		case PRICE : labelErrorPrice.setVisible(false);break;
		default:
			break;
		}
	}

	@Override
	public void hideAllError() {
		labelErrorQuantity.setVisible(false);
		labelGeneralError.setVisible(false);
		labelErrorPrice.setVisible(false);
	}

	@Override
	public void setInputEditView(boolean isEdit) {
		if(isEdit){
			buttonUpdate.setVisible(true);
			buttonSubmit.setVisible(false);
			buttonReset.setVisible(false);
			inputGoodsSelect.setEnabled(false);
			layoutButtonNav.setVisible(false);
			
		}else{
			inputGoodsSelect.setEnabled(true);
			buttonUpdate.setVisible(false);
			buttonSubmit.setVisible(true);
			buttonReset.setVisible(true);
			layoutButtonNav.setVisible(true);
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
	public void buttonClick(ClickEvent event) {
		if(event.getSource()==buttonReset){
			listener.buttonReset();
		}else if(event.getSource()==buttonSubmit){
			listener.buttonSave();
		}else if(event.getSource()==buttonCancel){
			listener.buttonCancel();
		}else if(event.getSource()==buttonUpdate){
			listener.buttonUpdate();
		}
		//tambahkan aksi
		else if(event.getSource()==buttonNext){
			nextView();
		}else if(event.getSource()==buttonAdd){
			addForm();
		}else if(event.getSource()==buttonPrev){
			prevView();
		}else if(event.getSource()==buttonRemove){
			remove();
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
	@Override
	public FormDeletion getFormData() {
		FormDeletion form=new FormDeletion(function);
		form.setDeletionDate(inputDeletionDate.getValue());
		form.setInformation(information.getValue());
		form.setQuantity(inputGoodsQuantity.getValue());
		form.setIdGoods((String) inputGoodsSelect.getValue());
		form.setDeletionId(this.deletionId);
		form.setPrice(inputGoodsPrice.getValue());
		return form;
	}

	@Override
	public void setFormData(DeletedGoods deletion) {
		this.deletionId=deletion.getIdDeletedGoods();
		buttonSubmit.setCaption("Simpan Perubahan");

		inputDeletionDate.setValue(deletion.getDeletionDate());
		information.setValue(deletion.getInformation());
		inputGoodsQuantity.setValue(deletion.getQuantity()+"");
		inputGoodsSelect.setValue(deletion.getGoods().getIdGoods());
		inputGoodsPrice.setValue(deletion.getPrice()+"");
		System.out.println("deletionDate "+deletion.getDeletionDate());
		System.out.println("inputGoodsQuantity "+deletion.getQuantity());
		System.out.println("deletionDate "+deletion.getPrice());
	}
	
	@Override
	public void setListener(InputDeletionListener listener) {
		this.listener=listener;
	}
	
	public void setPriceGuideValue(String content){
		this.labelPriceGuide.setValue(content);
	}
}
