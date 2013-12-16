package com.binar.core.procurement.purchaseOrder.editPurchaseOrder;

import java.util.Date;
import java.util.Map;

import com.binar.entity.Goods;
import com.binar.entity.PurchaseOrder;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class EditPurchaseOrderViewImpl extends FormLayout 
	implements EditPurchaseOrderView, ValueChangeListener, Button.ClickListener{

	private TextField inputNumber;
	private DateField inputDate;
	private TextArea inputName;
	private ComboBox comboUserResponsible;
	private TextField inputRayon;
	private int idPurchase;
	
	GeneralFunction function;
	private Button buttonSave;
	private Button buttonCancel;
	private Label labelError;

	public EditPurchaseOrderViewImpl(GeneralFunction function) {
		this.function=function;
		
	}
	@Override
	public void init() {
		inputNumber=new TextField("Nomor Surat");
					inputNumber.setDescription("Nomor Surat");
					inputNumber.setImmediate(true);
					inputNumber.setWidth(function.FORM_WIDTH);
					inputNumber.setEnabled(false);
					inputNumber.addValueChangeListener(this);

	    inputDate=new DateField("Tanggal Surat");
	    		 inputDate.setDescription("Tanggal Surat, format : dd-MM-yyyy");
	    		 inputDate.setImmediate(true);
	    		 inputDate.setWidth(function.FORM_WIDTH);
	    		 inputDate.setDateFormat("dd-MM-yyyy");
	 			inputDate.addValueChangeListener(this);
	    inputRayon=new TextField("Rayon");
			inputRayon.setDescription("Rayon");
			inputRayon.setImmediate(true);
			inputRayon.setWidth(function.FORM_WIDTH);
			inputRayon.addValueChangeListener(this);
	    inputName=new TextArea("Nama");
			inputName.setDescription("Nama dari surat pesanan");
			inputName.setImmediate(true);
			inputName.setWidth(function.FORM_WIDTH);
			inputName.addValueChangeListener(this);

	    comboUserResponsible =new ComboBox("Penanggung Jawab");
				comboUserResponsible.setDescription("Penanggung jawab surat ini");
				comboUserResponsible.setWidth(function.FORM_WIDTH);
				comboUserResponsible.addValueChangeListener(this);

		buttonCancel =new Button("Batal");
				  buttonCancel.addClickListener(this);
		buttonSave=new Button("Simpan");
					 buttonSave.addClickListener(this);
					 

		labelError=new Label();
		labelError.setVisible(false);
		labelError.addStyleName("form-error");
		labelError.setContentMode(ContentMode.HTML);

		
		construct();
		
	}

	@Override
	public void construct() {
		setMargin(true);
		this.setSpacing(true);
		
		this.addComponent(inputNumber);
		this.addComponent(inputName);
		this.addComponent(comboUserResponsible);
		this.addComponent(inputDate);
		this.addComponent(inputRayon);
		this.addComponent(labelError);
		this.addComponent(new GridLayout(2,1){
			{
				this.setMargin(true);
				this.setSpacing(true);
				this.addComponent(buttonSave, 0,0);
				this.addComponent(buttonCancel, 1, 0);
			}
		});
		
	}

	PurchaseOrder order;
	@Override
	public void setFormData(PurchaseOrder data) {
		this.order=data;
		inputNumber.setValue(data.getPurchaseOrderNumber());
		inputDate.setValue(data.getDate());
		inputRayon.setValue(data.getRayon());
		comboUserResponsible.setValue(data.getUserResponsible().getIdUser());
		inputName.setValue(data.getPurchaseOrderName());
		idPurchase=data.getIdPurchaseOrder();
		
	}

	@Override
	public FormData getFormData() {
		FormData data=new FormData();
		data.setDate(inputDate.getValue());
		data.setNumber(inputNumber.getValue());
		data.setRayon(inputRayon.getValue());
		data.setUserResponsible((Integer)comboUserResponsible.getValue());
		data.setIdPurchaseOrder(idPurchase);
		data.setName(inputName.getValue());
		return data;
	}

	@Override
	public void resetForm() {
		inputDate.setValue(new Date());
		inputNumber.setValue("");
		inputRayon.setValue("");
		inputName.setValue("");
		idPurchase=0;
	}

	@Override
	public void showError(String content) {
		labelError.setVisible(true);
		labelError.setValue(content);
	}

	@Override
	public void hideError() {
		labelError.setVisible(false);
	}
	@Override
	public void setComboBoxData(Map<Integer, String> data) {
        for (Map.Entry<Integer, String> entry : data.entrySet()) {
        	comboUserResponsible.addItem(entry.getKey());
        	comboUserResponsible.setItemCaption(entry.getKey(), entry.getValue());
        	comboUserResponsible.setValue(entry.getKey());
        }
	}

	EditPurchaseOrderListener listener;
	@Override
	public void setListener(EditPurchaseOrderListener listener) {
		this.listener=listener;
	}
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonCancel){
			listener.buttonClick("cancel");
		}else{
			listener.buttonClick("submit");
		}
	}
	@Override
	public void valueChange(ValueChangeEvent event) {
		listener.valueChange();
	}

	
}
