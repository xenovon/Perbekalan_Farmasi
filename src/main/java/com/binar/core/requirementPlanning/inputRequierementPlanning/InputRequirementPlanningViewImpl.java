package com.binar.core.requirementPlanning.inputRequierementPlanning;

import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class InputRequirementPlanningViewImpl extends VerticalLayout implements InputRequirementPlanningView {

	GeneralFunction generalFunction;

	private Label labelSelectMonth;
	private Label labelTitle;
	private Table table;
	private Button buttonInput;
	private ComboBox selectMonth;
	
	public InputRequirementPlanningViewImpl(GeneralFunction function){
		this.generalFunction=function;
	}
	
	public void init(){
		labelSelectMonth = new Label("<b>Pilih Periode Bulan : &nbsp</b>", ContentMode.HTML);
		labelTitle =new Label("<h2>Input Rencana Kebutuhan</h2>", ContentMode.HTML);
		
		buttonInput =new Button("Input Data Baru");
		buttonInput.setDescription("Input Data baru untuk bulan terpilih");
		
		selectMonth =new ComboBox("", generalFunction.getListFactory().createMonthListFromNow(10));
		selectMonth.addStyleName("non-caption-form");
		selectMonth.setWidth("180px");
		

		table=new Table();

		table.setSizeFull();
        table.setWidth("100%");
        table.setSortEnabled(true);
        

		table.addContainerProperty("No",Integer.class, null);
		table.addContainerProperty("Nama Barang",String.class, null);
		table.addContainerProperty("Perkiraan Harga",String.class, null);
		table.addContainerProperty("Kebutuhan",Integer.class, null);
		table.addContainerProperty("Perkiraan Jumlah Harga",String.class, null);
		table.addContainerProperty("Produsen",String.class, null);
		table.addContainerProperty("Distributor",String.class, null);
		table.addContainerProperty("Disetujui?",CheckBox.class, null);
		table.addContainerProperty("Jumlah Disetujui",Integer.class, null);
		table.addContainerProperty("Operasi", CssLayout.class, null);
		construct();
	}
	
	public void construct(){
		this.setMargin(true);
		this.setSpacing(true);
		this.addComponent(labelTitle);
		this.addComponent(new GridLayout(2, 1){
			{
				addComponent(labelSelectMonth,0,0);
				addComponent(selectMonth,1,0);
			}
		});
		this.addComponent(buttonInput);
		this.addComponent(table);
	}
}