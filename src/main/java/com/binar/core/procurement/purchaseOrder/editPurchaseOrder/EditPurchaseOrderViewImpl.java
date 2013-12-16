package com.binar.core.procurement.purchaseOrder.editPurchaseOrder;

import com.binar.entity.Goods;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class EditPurchaseOrderViewImpl extends FormLayout implements EditPurchaseOrderView{

	private TextField inputNumber;
	private DateField inputDate;
	private ComboBox comboUserResponsible;
	private TextField inputRayon;
	
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

	    inputDate=new DateField("Tanggal Surat");
	    		 inputDate.setDescription("Tanggal Surat");
	    		 inputDate.setImmediate(true);
	    		 inputDate.setWidth(function.FORM_WIDTH);
	    inputRayon=new TextField("Rayon");
			inputRayon.setDescription("Rayon");
			inputRayon.setImmediate(true);
			inputRayon.setWidth(function.FORM_WIDTH);
			inputRayon.setEnabled(false);

	    comboUserResponsible =new ComboBox("Penanggung Jawab");
				comboUserResponsible.setDescription("Penanggung jawab surat ini");
				comboUserResponsible.setWidth(function.FORM_WIDTH);
	    
	}

	@Override
	public void construct() {
		// TODO Auto-generated method stub
		
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
	public void setComboBoxData(ComboDataList list) {
		// TODO Auto-generated method stub
		
	}

	EditPurchaseOrderListener listener;
	@Override
	public void setListener(EditPurchaseOrderListener listener) {
		this.listener=listener;
	}

	
}
