package com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm;

import java.awt.peer.TextFieldPeer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm.InputFormView.ErrorLabel;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

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
	private CheckBox isPPN;
	//untuk label unit di samping jumlah kebutuhan, isi sesuai jenis barang
	private Label labelSatuan;
	private Button buttonReset;
	private Button buttonSubmit;
	private Button buttonCancel;
	private Button buttonNext;
	private Button buttonPrev;
	private Button buttonAdd;
	private Button buttonRemove;
	
	private Label labelErrorQuantity; //untuk error realtime
 	private Label labelErrorPrice; //untuk error realtime
	private Label labelGeneralError; //error saat tombol submit ditekan
	private InputFormListener listener;

	GeneralFunction function;
	String periode="";
	private List<FormData> formDataList;
	public InputFormViewImpl(GeneralFunction function){
		this.function=function;
	}
	public InputFormViewImpl(GeneralFunction function, String periode){
		this.function=function;
		this.periode=periode;
	}
	public void setPeriode(String periode) {
		this.periode = periode;
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
		inputGoodsQuantity=new TextField("Jumlah Kebutuhan *)"){
			{
				setWidth(function.FORM_WIDTH);
				setImmediate(true);
			}
		};
		// TODO tambahkan komponen label error
		inputGoodsQuantity.addValueChangeListener(this);
		inputGoodsSelect=new ComboBox("Nama Barang *)"){
			{
				setImmediate(true);
				setWidth(function.FORM_WIDTH);
			}
		};
		inputGoodsSelect.addValueChangeListener(this);

		inputManufacturer =new ComboBox("Produsen *)"){
			{
				setWidth(function.FORM_WIDTH);
				setImmediate(true);
			
			}
		};
		inputManufacturer.addValueChangeListener(this);
		inputSupplier =new ComboBox("Distributor *)"){
			{
				setImmediate(true);
				setWidth(function.FORM_WIDTH);
			}
		};
		inputSupplier.addValueChangeListener(this);
		inputPrice= new TextField("Harga Barang *)"){
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
		
		isPPN=new CheckBox("Harga Termasuk PPN");
		isPPN.setValue(true);
		isPPN.addValueChangeListener(this);		
		isPPN.setImmediate(true);

		buttonReset=new Button("Reset");
		buttonReset.addClickListener(this);

		buttonSubmit=new Button("Simpan");
		buttonSubmit.addClickListener(this);
		buttonSubmit.addStyleName("primary");
		
		buttonCancel =new Button("Batal");
		buttonCancel.addClickListener(this);
		
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
	boolean isEditMode=false;
	int idReqPlanning=0;
	//set data yang mesti diedit
	public void setDataEdit(ReqPlanning data){
		
		//untuk kebutuhan edit
		isEditMode=true;
		idReqPlanning=data.getIdReqPlanning();

		inputGoodsQuantity.setValue(String.valueOf(data.getQuantity()));
		inputGoodsSelect.setValue(data.getSupplierGoods().getGoods().getIdGoods());
		inputManufacturer.setValue(String.valueOf(data.getSupplierGoods().getManufacturer().getIdManufacturer()));
		inputSupplier.setValue(String.valueOf(data.getSupplierGoods().getSupplier().getIdSupplier()));
		
		inputPrice.setValue(String.valueOf(data.getPriceEstimationPPN()));
		isPPN.setValue(true);
		
		inputInformation.setValue(data.getInformation());
		buttonSubmit.setCaption("Simpan Perubahan");
		
		layoutButtonNav.setVisible(false);
	}
	//restore dari posisi edit ke submit;
	public void setDataSubmit(){
		buttonSubmit.setCaption("Submit");
		resetForm();
	}
	GridLayout layoutButtonNav;
	/* untuk menkonstruksi tampilan */
	private void construct(){
		setMargin(true);
		this.setSpacing(true);
		formDataList=new ArrayList<FormData>();
					
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
		this.addComponent(isPPN);
		this.addComponent(labelErrorPrice);
		this.addComponent(inputInformation);
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
		
		this.addComponent(new GridLayout(3, 1){
			{
				addComponent(buttonSubmit, 0, 0);
				addComponent(buttonReset, 1, 0);
				addComponent(buttonCancel, 2, 0);
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
	public List<FormData> getFormDataList() {
		return formDataList;
	}
	//METHOD UTAMA
	public void addForm(){
		FormData data=getFormData();
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
		FormData newData=getFormData();
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
		FormData data;
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
			FormData data=formDataList.get(currentPos-1);
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
		formDataList=new ArrayList<FormData>();
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
	private boolean validate(FormData data){
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
			FormData data=formDataList.get(pos-1);
			setData(data);
		}
	}
	private FormData getFormData(){
		FormData data=new FormData(function);
		data.setGoodsId((String)getInputGoodsSelect().getValue());
		data.setInformation(getInputInformation().getValue());
		data.setManufacturId((String)getInputManufacturer().getValue());
		data.setPrice(getInputPrice().getValue());
		data.setQuantity(getInputGoodsQuantity().getValue());
		data.setSupplierId((String)getInputSupplier().getValue());
		data.setPpn(getIsPPN().getValue());
		data.setPeriode(periode);
		return data;
	}
	private FormData getFormData(FormData data){
		data.setGoodsId((String)getInputGoodsSelect().getValue());
		data.setInformation(getInputInformation().getValue());
		data.setManufacturId((String)getInputManufacturer().getValue());
		data.setPrice(getInputPrice().getValue());
		data.setQuantity(getInputGoodsQuantity().getValue());
		data.setSupplierId((String)getInputSupplier().getValue());
		data.setPpn(getIsPPN().getValue());
		data.setPeriode(periode);

		return data;
	}
	//MEngeset data dari FormData ke form
	private void setData(FormData data){
		//untuk kebutuhan edit
		inputGoodsQuantity.setValue(String.valueOf(data.getQuantity()));
		inputGoodsSelect.setValue(data.getGoodsId());
		inputManufacturer.setValue(data.getManufacturId());
		inputSupplier.setValue(data.getSupplierId());
		
		inputPrice.setValue(data.getPrice());
		isPPN.setValue(data.isPpn());
		
		inputInformation.setValue(data.getInformation());		
	}
	//-------------------------------Kode tambahan untuk mengatur tampilan form  //

	public void addListener(InputFormListener listener){
		this.listener=listener;
	}
	public void resetForm(){

		this.inputGoodsQuantity.setValue("");
		this.inputGoodsSelect.setValue(null);
		this.inputInformation.setValue("");
		this.inputPrice.setValue("");
		this.inputManufacturer.setValue(null);
		this.inputSupplier.setValue(null);
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
		}else if(event.getProperty()==isPPN){
			if(isEditMode){
				listener.changePrice(isPPN.getValue(), idReqPlanning);
			}
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
	
	Window window;

	public Window displayForm(Component content, String title){
		//menghapus semua window terlebih dahulu
		if(window==null){
			window=new Window(title){
				{
					center();
					setClosable(false);
					setWidth("70%");
					setHeight("80%");
				}
			};

		}
		this.getUI().removeWindow(window);
		window.setCaption(title);
		window.setContent(content);
		this.getUI().addWindow(window);
		return window;
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
	
	public CheckBox getIsPPN() {
		return isPPN;
	}
	
	public static void main(String[] args) {
		List<String> anu=new ArrayList<String>();
		anu.add("A");
		anu.add("B");
		anu.add("C");
		anu.add("D");
		System.out.println(anu.toString());
		
		anu.set(0, "X");
		anu.remove(2);
		anu.set(2,"F");
		System.out.println(anu.toString());
	}
	
}
