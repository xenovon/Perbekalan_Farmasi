package com.binar.core.procurement.invoice.newInvoice;

import java.util.Date;
import java.util.Map;

import com.binar.core.procurement.purchaseOrder.newPurchaseOrder.NewPurchaseOrderView.FormViewEnum;
import com.binar.entity.Invoice;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class NewInvoiceViewImpl extends Window implements NewInvoiceView{

	private GeneralFunction function;
	
	private FormLayout formSelectPurchaseOrder;
	private VerticalLayout viewInvoice;
	private VerticalLayout mainLayout; //isinya adalah button dan form/view invoice
	private DateField inputRangeStart;
	private DateField inputRangeEnd;
	private ComboBox comboPurchaseOrder;
	
	private Button buttonNext;
	private Button buttonBack;
	private Button buttonCreate;
	private Button buttonCancel;
	
	private FormViewEnum position;

	
	public NewInvoiceViewImpl(GeneralFunction function) {
		this.function=function;
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void construct() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListener(NewInvoiceListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSelectedPeriod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkReqPlanning(boolean isChecked) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setComboPurchaseOrder(Map<Integer, String> data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateInvoiceView(Invoice data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FormData getEditedInvoice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetForm() {
		// TODO Auto-generated method stub
		
	}
	public Label getLabelError(){
		return null;
	}
	public void setPosition(){
		
	}
	@Override
	public void setFormView(FormViewEnum formView) {
		// TODO Auto-generated method stub
		
	}
	public Date getRangeStart(){
		return inputRangeStart.getValue();
	}
	public Date getRangeEnd(){
		return inputRangeEnd.getValue();
	}
	
}
